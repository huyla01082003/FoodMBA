<?php
header ("Access-Control-Allow-Origin: *");
header ("Content-Type: application/json; charset=UTF-8");
header ("Access-Control-Allow-Methods: POST");
header ("Access-Control-Max-Age: 3600");
header ("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

$host="localhost";
$username = "root";
$password = "";
$db= "foodreview";

$response = array("error" => false);
$conn=@mysqli_connect($host, $username, $password, $db) or die(mysqli_error);

// get posted data
$sId;
$sName = $_POST["key_sName"];
$sAddress = $_POST["key_sAddress"];
$sNumber = $_POST["key_sNumber"];
$sOpen = $_POST["key_sOpen"];
$sClose = $_POST["key_sClose"];
$sCategory = $_POST["key_sCategory"];
$sImage;
$sImageMap;

$sql = "insert into shop values('$sId','$sName','$sAddress','$sNumber','$sOpen','$sClose','$sCategory','$sImage','$sImageMap')";
$result = mysqli_query($conn, $sql);
if($result){
	echo "success";
}
else{
	echo "fail";
}
?>