import sys
import networkx as nx
import functools
from networkx.drawing.nx_agraph import write_dot, graphviz_layout
import numpy as np
from scipy.cluster.hierarchy import dendrogram, linkage,fcluster
from matplotlib import pyplot as plt
from hw import edge_betweenness,girvan_newman 
import graphviz



################################################################################
### HELPER ROUTINES
################################################################################


# use networkx.draw to plot the layout the graph
def plot_graph(G,title):
	plt.title(title + ' Layout')
	pos =graphviz_layout(G, prog='dot')
	nx.draw(G, pos, with_labels=True, arrows=True)
	# plt.figure(figsize=(10, 6))
	plt.savefig(title.split()[0].lower() + '-layout.png')
	# plt.show()  Uncomment if you want to see the plot



# Show dendrogram for your implementation
def plot_dendrogram(G, edges, title, ignore_weight=False):
	# Convert edge/bw-scores into Z matrix (linkage matrix)
	uf = nx.utils.UnionFind()
	sz = { x : 1 for x in G.nodes }
	_ = [ uf[x]  for x in G.nodes ] # Initialize union find with singleton sets
	h = { x : x  for x in G.nodes }
	Z = []
	cnt = len(G.nodes)
	j = 1.0
	for (v1,v2,bw) in reversed(edges): # Agglomerative
		v1 = v1[0] if type(v1) == list else v1  # Enter list if needed
		v2 = v2[0] if type(v2) == list else v2  # Enter list if needed
		if uf[v1] == uf[v2]:
			# Skip edges that don't connect new components
			continue
		nsz = sz[uf[v1]] + sz[uf[v2]]
		# Create the linkage matrix
		w1,w2 = h[uf[v1]],h[uf[v2]]
		Z.append ([w1 if w1 < w2 else w2, w2 if w1 < w2 else w1, j if ignore_weight else bw, nsz])
		uf.union (v1,v2)
		sz[uf[v1]] = nsz # Track cluster size
		h[uf[v1]] = cnt  # Label cluster
		j += 1.0
		cnt += 1
	# Draw the Dendrogram
	fig = plt.figure(figsize=(10, 6))
	Z = np.array(Z)
	dn = dendrogram(Z)
	plt.title(title + " Hierarchical Clustering")
	plt.savefig(title.split()[0].lower() + "-dendrogram" + ("-ref" if "Reference" in title else "-your") + ".png")
	# plt.show()  Uncomment if you want to see the dendrogram

	return Z
	



# Test if your solution is OK relative to reference
def compare_clustering(G, edges, title):
	def wrap_bw(H):
		bw = nx.edge_betweenness_centrality(H, normalized=False)
		mx = max(bw.values())
		for e in sorted(bw.keys()):
			if bw[e] == mx:
				return e
		#return max(bw, key=bw.get)

	### REFERENCE SOLUTION
	splits_ref = list (nx.algorithms.community.centrality.girvan_newman(G, most_valuable_edge=wrap_bw))
	# Going in reverse, find which groups get merged
	df = [ [ sorted(list(s)) for s in splits_ref[-i] if s not in splits_ref[-i-1] ] for i in range(1,len(splits_ref)) ]
	df += [ [ [sorted(list(splits_ref[0][0]))[0]], [sorted(list(splits_ref[0][1]))[0]] ] ] 
	# Find the subgraphs so we can find betweenness
	df = [ (df[i][0],df[i][1],i+1.0) for i in range(len(df)) ]
	Z_ref = plot_dendrogram (G, list(reversed(df)), title + " - Reference Solution", ignore_weight=True)

	### YOUR SOLUTION
	Z_gn = plot_dendrogram (G, edges, title + " - Your Solution", ignore_weight=True)

	# Compare the splits
	if np.max(abs(Z_gn - Z_ref)) > 1e-5:
		print ("[-] Your Girvan-Newman implementation differs from the reference solution on this graph.")
		print ("    Your linkage matrix:\n{}".format (Z_gn))
		print ("    Reference linkage matrix:\n{}".format (Z_ref))
	else:
		print ("[+] Your Girvan-Newman implementation matches the reference solution output on this graph!")



# Test the betweenness implementation
def compare_betweenness(G):

	### REFERENCE IMPLEMENTATION
	bx = nx.edge_betweenness_centrality (G, normalized=False)

	### YOUR IMPLEMENTATION
	bw = edge_betweenness (G)

	diff = set(bw.keys()).symmetric_difference (bx.keys())
	errors = 0
	if len(diff) > 0:
		print ("[-] The set of edges with betweenness scores differs from the reference implementation.")
		print ("    Edges that were different: {}".format (list(diff)))
		return

	for e in bx:
		if abs(bx[e] - bw[e]) > 1e-6:
			print ("[-] Betweenness of edge {} differs. Yours: {}, Reference: {}".format(e,bw[e],bx[e]))
			errors += 1
		if errors >= 6:
			print ("[...] (more examples omitted)")
			break

	print ("[+] Betweenness scores match reference implementation on this graph!")
	


#################################### MAIN ################################################

def main():
	graphs = [ ("Windmill", nx.windmill_graph(3,3)),
	           ("Path", nx.path_graph(10)),
		   ("Complete", nx.complete_graph(5)),
		   ("Random", nx.erdos_renyi_graph(30,0.6))
		 ]

	for title,G in graphs:

		print ("\n\n" + "=" * 80)
		print ("=== " + title + " Graph ===")
		print ("=" * 80)

		# Use networkx.draw to plot the layout the graph
		plot_graph(G, title)

		# Test if the betweenness is computed correctly
		compare_betweenness(G)

		edges = girvan_newman(G)

		# Evaluate the solution by comparing it to the reference implementation
		compare_clustering (G, edges, title)


if __name__ == "__main__":
	main()


