<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8);
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, X-Requested-With");
include 'database.php';
include 'user.php';
$response = array("error" => FALSE);
$database = new Database();
$db = $database->getConnection();
$shop = new Shop($db);

$data = json_decode(file_get_contents("php://input"));

	$shop->sId = $data->sId;
	$shop->sName = $data->sName;
	$shop->sAddress = $data->sAddress;
	$shop->sNumber = $data->sNumber;
	$shop->sOpen = $data->sOpen;
	$shop->sClose = $data->sClose;
	$shop->sCategory = $data->sCategory;
	$shop->sImage = $data->sImage;
	$shop->sRate = $data->sRate;
	if($shop->inserData()){
		$response["error"] = FALSE;
		$response["message"]="Successful";
		echo json_encode($response);	
	}
	else{
		$response["error"] = TRUE;
		$response["error_msg"] = "Them that bai";
		echo json_encode($response);
	}
?>