<?php

$host = 'localhost';
$username = 'root';
$pwd = '';
$db = '';


$con = mysqli_connect($host, $username, $pwd, $db) or die ('Unable to connect');
 mysqli_query($con,"SET CHARSET utf8");
    mysqli_query($con,"SET NAMES utf8 COLLATE utf8_polish_ci");

$query = mysqli_query($con, "SELECT * FROM mot");

//while($show = mysqli_fetch_array($get)):
//echo $show['mott']."<br>";
//endwhile;
$result = mysqli_query($con, $query);
	$number_of_rows = mysqli_num_rows($result);
$temp_array  = array();
if($number_of_rows > 0)
{
  while($row=mysqli_fetch_array($result)) 
  {
      $temp_array[] = $row;
  }
    
    header('Content-Type: application/json');
	echo json_encode(array("mott"=>$temp_array));
    
}
mysqli_close($con);

?>
