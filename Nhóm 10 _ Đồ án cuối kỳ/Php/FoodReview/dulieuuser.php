<?php
header("Access-Control-Allow-Origin:*");
header("Content-Type: application/json; charset=UTF-8");
include 'database.php';
include 'user.php';
$database=new Database();
$db=$database->getConnection();

$p=new User($db);
$stmt=$p->getData();
$num=$stmt->rowCount();
if($num>0)
{
	$manguser= array();
	$manguser["users"]= array();
	while($row=$stmt->fetch(PDO::FETCH_ASSOC))
	{
		extract($row);
		$user=array(
			"id"=>$id,
			"username"=>$username,
			"password"=>$password,
			"email"=>$email,
			"contact"=>$contact,
			"address"=>$address,
			"image"=>base64_encode($image),

		);
		array_push($manguser["users"],$user);
	}
	echo json_encode($manguser);
}
else
{
	echo json_encode(array("message"=>"Khong co user nao"));
}
?>