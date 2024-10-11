<?php
header("Access-Control-Allow-Origin:*");
header("Content-Type: application/json; charset=UTF-8");
include 'database.php';
include 'shop.php';
$database=new Database();
$db=$database->getConnection();

$p=new Shop($db);
$stmt=$p->getData();
$num=$stmt->rowCount();
if($num>0)
{
	$mangshop= array();
	$mangshop["shops"]= array();
	while($row=$stmt->fetch(PDO::FETCH_ASSOC))
	{
		extract($row);
		$shop=array(
			"Id"=>$sId,
			"Name"=>$sName,
			"Address"=>$sAddress,
			"Number"=>$sNumber,
			"Open"=>$sOpen,
			"Close"=>$sClose,
			"Category"=>$sCategory,
			"Image"=>base64_encode($sImage),
			"ImageMap"=>base64_encode($sImageMap)
		);
		array_push($mangshop["shops"],$shop);
	}
	echo json_encode($mangshop);
}
else
{
	echo json_encode(array("message"=>"Khong co quan nao"));
}
?>