<?php
class User
{
	private $conn;
	private $table_name="user";
	public $id;
	public $username;
	public $password;
	public $email;
	public $contact;
	public $address;
	public $image;
	
	public function __construct ($db)
	{
		$this->conn=$db;
	}
	function getData()
	{
		$sql="SELECT * FROM ". $this->table_name;
		$stmt = $this->conn->prepare($sql);
		$stmt->setFetchMode (PDO::FETCH_ASSOC);
		$stmt->execute();
		return $stmt;
	}
	function insertData()
	{
		$sql="INSERT INTO ".$this->table_name." VALUES (;'".$this->id ."','".$this->username ."','". $this->password ."','". $this->email ."','". $this->contact ."','". $this->address ."','". $this->image .")";
		$stmt = $this->conn->prepare($sql);
		if($stmt->execute())
			return true;
		else
			return false;
	}
}
?>