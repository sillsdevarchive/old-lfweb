<?php
namespace dashboardtool;

class DashboardDbType
{
	const DB_TEST  = 0;
	const DB_MYSQL = 1;
}

class DashboardToolFactory
{
	public static function getDashboardDbAccess($dbType)
	{
		switch ($dbType) {
			case DashboardDbType::DB_MYSQL:
				return new DashboardToolDbAccessMySql();
			case DashboardDbType::DB_TEST:
				throw new \Exception('Not implemented');
				break;
			default:
				throw new \Exception("undefined database type");
			break;
		}
	}
}

?>