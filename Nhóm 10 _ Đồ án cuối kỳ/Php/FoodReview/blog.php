<?php
class Blog
{
	private $conn;
	private $table_name="blog";
	public $bId;
	public $uName;
	public $bContent;
	
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
		$sql="INSERT INTO ".$this->table_name." VALUES (;'".$this->bId ."','".$this->uName ."','". $this->bContent .")";
		$stmt = $this->conn->prepare($sql);
		if($stmt->execute())
			return true;
		else
			return false;
	}
}
?>