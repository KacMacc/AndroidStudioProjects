<?php

$host = 'localhost';
$username = 'root';
$pwd = '';
$db = '';


$con = mysqli_connect($host, $username, $pwd, $db) or die ('Unable to connect');
 mysqli_query($con,"SET CHARSET utf8");
    mysqli_query($con,"SET NAMES utf8 COLLATE utf8_polish_ci");


$query = "Select mott from mot";

$result = mysqli_query($con, $query);

$c = mysqli_num_rows($result);
$rand = rand(0, $c)-1;

$get = mysqli_query($con, "SELECT mott FROM mot WHERE id > '$rand' LIMIT 1");



while($row = mysqli_fetch_assoc($get))
{
    $array[] = $row;
}

header ('Connect-Type:Application/json');

echo json_encode($array);
?>
