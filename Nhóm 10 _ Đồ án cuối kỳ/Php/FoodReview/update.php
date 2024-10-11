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
$idString = $_POST["key_id"];
$id =  (int) $idString;
$email= $_POST["key_email"];
$contactString = $_POST["key_contact"];
$contact = (int) $contactString;
$address = $_POST["key_address"];


$sql = "update user set email = '$email', contact = $contact, address = '$address' where id = $id";
$result = mysqli_query($conn, $sql);
if($result){
	echo "success";
}
else{
	echo "fail";
}
?>