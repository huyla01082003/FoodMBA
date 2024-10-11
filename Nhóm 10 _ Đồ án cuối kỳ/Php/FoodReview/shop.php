<?php
class Shop
{
	private $conn;
	private $table_name="shop";
	public $sId;
	public $sName;
	public $sAddress;
	public $sNumber;
	public $sOpen;
	public $sClose;
	public $sCategory;
	public $sImage;
	public $sImageMap;
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
		$sql="INSERT INTO ".$this->table_name." VALUES (;'".$this->sId ."','". $this->sName ."','". $this->sAddress ."','". $this->sNumber ."','". $this->sOpen ."','". $this->sClose ."','". $this->sCategory ."','". $this->sImage ."','". $this->sImageMap .")";
		$stmt = $this->conn->prepare($sql);
		if($stmt->execute())
			return true;
		else
			return false;
	}
}
?>