<?php
if($_SERVER['REQUEST_METHOD'] == 'POST') {
    //echo "Jestem";
    
	$ids = explode(";", $_POST['ids']);
	$odp = explode(";", $_POST['odp']);
	
	$id1 = $ids[0];
	$id2 = $ids[1];
	$id3 = $ids[2];
	
	$odp1 = $odp[0];
	$odp2 = $odp[1];
	$odp3 = $odp[2];
   //echo $odp1;
   // echo $odp2;
   // echo $odp3;
    
   
	
	$host = 'localhost';
	$username = 'root';
	$pwd = '';
	$db = '';

	$con = mysqli_connect($host, $username, $pwd, $db) or die ('Unable to connect');
	
	//mysqli_query($con,"SET CHARSET utf8");
	//mysqli_query($con,"SET NAMES utf8 COLLATE utf8_polish_ci");
    
	//echo "start2";
    
    //$query = $db->query("SELECT * FROM mot ORDER BY RAND() LIMIT 1");
    //$myrow = $query->fetch_array();
    
	$query = $con->query("SELECT * FROM quiz WHERE id='$id1' AND poprOdp='$odp1' OR id='$id2' AND poprOdp='$odp2' OR id='$id3' AND poprOdp='$odp3'");
    //$res=mysql_query($query);

    
    //echo "start3";
     
    $s=0;
    
//$myrow = $query->fetch_array();
    
//echo $myrow['mott'];
if($query)
    while($myrow = $query->fetch_array())
{
    //while($myrow = $query->fetch_array())
    $s++;
    }
    
   
    //$result = $con->query("SELECT * FROM quiz WHERE (id='$id1' AND poprOdp='$odp1')");
	
//	if(!$result) 
//    {
//		throw new Exception($con->error);
//	}
	
//    $num_rows = mysql_num_rows($con,$result);
//	echo($result);
   
    
$wyslij = $con->query("INSERT INTO odp (id, pkt) VALUES (NULL, '$s')");

if(!$wyslij) {
		throw new Exception($con->error);
	}
	
   mysqli_close($con); 
    
    if($s==3){
echo "Gratulację, zdobyłeś ".$s." pkt!";
    }
        if($s==2){
echo "Całkiem nieźle, zdobyłeś ".$s." pkt!";
    }
      if($s==1){
echo "Słabiutko, zdobyłeś ".$s." pkt :(";
    }
    if($s==0){
echo "Tragedia, zdobyłeś ".$s." pkt";
    }

}
//INSERT INTO odp (id, pkt) VALUES (NULL, '$result->num_rows');

//mysqli_close($con);
?>
