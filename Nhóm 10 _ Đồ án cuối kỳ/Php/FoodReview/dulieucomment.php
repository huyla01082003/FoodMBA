<?php
header("Access-Control-Allow-Origin:*");
header("Content-Type: application/json; charset=UTF-8");
include 'database.php';
include 'comment.php';
$database=new Database();
$db=$database->getConnection();

$p=new Comment($db);
$stmt=$p->getData();
$num=$stmt->rowCount();
if($num>0)
{
	$mangcomment= array();
	$mangcomment["comments"]= array();
	while($row=$stmt->fetch(PDO::FETCH_ASSOC))
	{
		extract($row);
		$comment=array(
			"cId"=>$cId,
			"sId"=>$sId,
			"cContent"=>$cContent
		);
		array_push($mangcomment["comments"],$comment);
	}
	echo json_encode($mangcomment);
}
else
{
	echo json_encode(array("message"=>"Khong co user nao"));
}
?>