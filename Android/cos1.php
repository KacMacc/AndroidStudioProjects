<?php

$host = 'localhost';
$username = 'root';
$pwd = '';
$db = '';


$con = mysqli_connect($host, $username, $pwd, $db) or die ('Unable to connect');
 mysqli_query($con,"SET CHARSET utf8");
    mysqli_query($con,"SET NAMES utf8 COLLATE utf8_polish_ci");

$count = mysqli_query($con, "SELECT * FROM mot");
$c = mysqli_num_rows($count);
$rand = rand(0, $c)-1;
$get = mysqli_query($con, "SELECT * FROM mot WHERE id > '$rand' LIMIT 1");

while($show = mysqli_fetch_array($get)):
echo $show['mott'];
endwhile;
//if($count)
//{
  //while($row=mysqli_fetch_array($get)) 
  //{
  //   $flag[]= $row;
  //}
    
 // print (json_encode($flag));
    
//}
//header('Location:index2.php');
mysqli_close($con);
    //header('Location:witaj.php');
    
?>
