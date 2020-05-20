'''
/* THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING CODE
WRITTEN BY OTHER STUDENTS.
Dillon Wu */
'''


from sklearn import datasets
import pandas as pd
import seaborn as sb
import matplotlib.pyplot as plt



# In[5]:


iris = datasets.load_iris()
type(iris)


# In[6]:


#Print out the data; but not sure what each of the columns mean
#Need some labels
#Sepal length, sepal width, pedal length, pedal width (source: youtube video)
print(iris.data)


# In[8]:


# Representing the columns above 
print (iris.feature_names)


# In[47]:


# 0 = satosa, 1 = versicolor, 2 = verginica
print (iris.target)
groups = list(iris.target)
print(groups)
sr = pd.Series(groups)
print(sr)


# In[45]:


print (iris.target_names)


# In[52]:


test = pd.DataFrame(iris.data, columns = ['Sepal Length', 'Sepal Width', 'Pedal Length', 'Pedal Width'])


# In[61]:


for k in groups:
    print(iris.target_names[k]) 
    print(k)
print(iris.target_names[0])


# In[59]:


test['groups'] = pd.Series([iris.target_names[k] for k in groups])
print(test)
print(test['groups'])


# In[68]:


Flowers = list(iris.target)
test['Flowers'] = pd.Series([iris.target_names[k] for k in Flowers])
bp = test.boxplot(column = 'Sepal Length', by='Flowers', return_type = 'axes')
print (bp)


# In[72]:


bp2 = test.boxplot(column = 'Sepal Width', by='Flowers', return_type = 'axes')
bp3 = test.boxplot(column = 'Pedal Length', by='Flowers', return_type = 'axes')
bp4 = test.boxplot(column = 'Pedal Width', by='Flowers')


# In[77]:


#Length on x, Width on y
#Sepal 
#test.plot.Scatter(x="Sepal Length", y="Sepal Width", group)
sb.pairplot(x_vars=["Sepal Length"], y_vars=["Sepal Width"], data=test, hue="Flowers", size=5)


# In[78]:


#Pedal
sb.pairplot(x_vars=["Pedal Length"], y_vars=["Pedal Width"], data=test, hue="Flowers", size=5)


# In[ ]:
plt.show()



