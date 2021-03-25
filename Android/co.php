<?php

$host = 'localhost';
$username = 'root';
$pwd = '';
$dbname = '';

$db = new mysqli($host, $username, $pwd, $dbname, 3306) or die ('Unable to connect');
$db->set_charset("utf8");

//$con = mysqli_connect($host, $username, $pwd, $db) or die ('Unable to connect');
 mysqli_query($db,"SET CHARSET utf8");
    mysqli_query($db,"SET NAMES utf8 COLLATE utf8_polish_ci");

$query = $db->query("SELECT * FROM mot ORDER BY RAND() LIMIT 1");
$myrow = $query->fetch_array();
echo $myrow['mott'];

?>
