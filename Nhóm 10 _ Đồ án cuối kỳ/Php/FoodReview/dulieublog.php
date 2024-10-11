<?php
header("Access-Control-Allow-Origin:*");
header("Content-Type: application/json; charset=UTF-8");
include 'database.php';
include 'blog.php';
$database=new Database();
$db=$database->getConnection();

$p=new Blog($db);
$stmt=$p->getData();
$num=$stmt->rowCount();
if($num>0)
{
	$mangblog= array();
	$mangblog["blogs"]= array();
	while($row=$stmt->fetch(PDO::FETCH_ASSOC))
	{
		extract($row);
		$blog=array(
			"bId"=>$bId,
			"uName"=>$uName,
			"bContent"=>$bContent
		);
		array_push($mangblog["blogs"],$blog);
	}
	echo json_encode($mangblog);
}
else
{
	echo json_encode(array("message"=>"Khong co blog nao"));
}
?>