<?php

$host = 'localhost';
$username = 'root';
$pwd = '';
$db = 'dupa';


$con = mysqli_connect($host, $username, $pwd, $db) or die ('Unable to connect');
 mysqli_query($con,"SET CHARSET utf8");
    mysqli_query($con,"SET NAMES utf8 COLLATE utf8_polish_ci");

$query = "SELECT * FROM mot";

//while($show = mysqli_fetch_array($get)):
//echo $show['mott']."<br>";
//endwhile;
$result = mysqli_query($con, $query);
//$number_of_rows = mysqli_num_rows($result);
//$temp_array  = array();
if($result)
{
  while($row=mysqli_fetch_array($result)) 
  {
      $flag[] = $row;
  }
    
	print (json_encode($flag));
    
}
mysqli_close($con);

?>