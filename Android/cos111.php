<?php
//header('Content-Type: text/html; charset=utf-8');

$host = 'localhost';
$username = 'root';
$pwd = '';
$db = '';

$con = mysqli_connect($host, $username, $pwd, $db) or die ('Unable to connect');
 mysqli_query($con,"SET CHARSET utf8");
    mysqli_query($con,"SET NAMES utf8 COLLATE utf8_polish_ci");


//$db = new mysqli($host, $username, $pwd, $dbname, 3306) or die ('Unable to connect');
//$db->set_charset("utf8");

$query = $con->query("SELECT * FROM quiz ORDER BY RAND() LIMIT 3");
//$myrow = $query->fetch_array();
//echo $myrow['pytanie'];
while ($myrow = $query->fetch_array())
{   
//if($myrow > 3)
//{
  
//}
    echo $myrow['id'];
    echo "/";
    echo $myrow['pytanie'];
    echo "/";
}

mysqli_close($con);
?>
