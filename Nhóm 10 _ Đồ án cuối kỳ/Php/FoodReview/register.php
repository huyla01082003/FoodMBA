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
$id;
$user = $_POST["key_username"];
$pass = $_POST["key_password"];
$email;
$contact;
$address;
$image;

$sql = "insert into user values('$id','$user','$pass','$email','$contact','$address','$image')";
$result = mysqli_query($conn, $sql);
if($result){
	echo "succes";
}
else{
	echo "fail";
}
?>