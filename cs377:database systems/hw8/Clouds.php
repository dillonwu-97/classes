

<?php 
#/*THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING A TUTOR OR CODE WRITTEN BY OTHER STUDENTS - #Dillon Wu*/
  $conn = mysqli_connect("holland.mathcs.emory.edu","cs377", "abc123");

  if (mysqli_connect_errno())            # -----------  check connection error
   {      
      printf("Connect failed: %s\n", mysqli_connect_error());
      exit(1);
   } 

   if ( ! mysqli_select_db($conn, "spjDB") )          # Select spjDB
   {      
      printf("Error: %s\n", mysqli_error($conn));
      exit(1);
   }

// contains the where values; how to access the "sname" portions??
   $spjWhere = array (
    "sname" => $_POST["sname"], 
    "supplier.city" => $_POST["scity"],
    "pname" => $_POST["pname"], 
    "part.city" => $_POST["pcity"],
    "jname" => $_POST["jname"],   
    "proj.city" => $_POST["jcity"]

  );

#aliasing
   $spjAlias = array ( 
    "sname" => "Supplier Name", 
    "supplier.city" => "Supplier City", 
    "pname" => "Part Name", 
    "part.city" => "Part City", 
    "jname" => "Project Name", 
    "proj.city" => "Project City",
    "qty" => "Quantity Shipped"
   );

   #key for the array = the name of the attributes 

   #Checking to see if the array works
   #foreach ($spjWhere as $i => $v) 
   # {
    #  print("key = $i , value A[$i] = $v \n");
    #}

   #Now, to make the query
   $query = "select distinct ";
   foreach ($spjWhere as $i => $v) {
    # if not empty
  #if ($spjWhere[$i]) {
        $query = $query . $i . ' "' ."$spjAlias[$i]". '" '.", ";
  #}
   }
   #remove the last comma and space
   #$query = substr($query, 0, -2);
   $query = $query . 'qty as "Quantity Shipped" from supplier, spj, proj, part where ';
   //comment
   foreach ($spjWhere as $i => $v) {
      if ($spjWhere[$i]) {
        if (stripos($v, '*')!== false || stripos($v, '?') !== false) {
          if (stripos($v, '?')) {
            $new = str_replace('?', '_', $v);
          }
          if (strippos($v, '*')) {
            $new = str_replace('*', '%', $v);
          }
          $query = $query . $i . " LIKE " .'"'. $new.'"'. " AND ";
        } else {
          $query = $query . $i . "=" . '"'. $v . '"'. " AND ";
        }
      }
    }

   #$query = substr($query, 0, -2);
   $query = $query . "supplier.snum = spj.snum AND proj.jnum = spj.jnum AND part.pnum = spj.pnum";


   #foreach ($spjWhere as $i => $v) {
   # $query = $query . $v; 
   #}

   #print($query);
    if ( ! ( $result = mysqli_query($conn, $query)) )      # Execute    query
   {      
      printf("Error: %s\n", mysqli_error($conn));
      exit(1);
   }    

#print($result);
#Returning the result
print("<UL>\n");
   print("<TABLE bgcolor=\"lightyellow\" BORDER=\"5\">\n");
      
   $printed = false;
while ( $row = mysqli_fetch_assoc( $result ) )
   {      
      if ( ! $printed )
      {   
     $printed = true;                 # Print header once...
      
     print("<TR bgcolor=\"red\">\n");
     foreach ($row as $key => $value)
     {
        print ("<TH>" . $key . "</TH>");             # Print attr. name
     }
     print ("</TH>\n");
      }   
      
      
      print("<TR>\n");
      foreach ($row as $key => $value)
      {   
     print ("<TD>" . $value . "</TD>");
      }   
      print ("</TR>\n");
   }   

   print("</TABLE>\n");
   print("</UL>\n");   
   print("<P>\n");   

?>

