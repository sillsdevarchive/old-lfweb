<?php
namespace libraries\lfdictionary\environment;

interface IEnvironmentMapper {
	
	/**
	 * 
	 * @param LFProjectAccess $projectAccess
	 */
	public function readLFProjectAccess($projectAccess);
	
	/**
	 * 
	 * @param LFProjectAccess $projectAccess
	 */
	public function writeLFProjectAccess($projectAccess);
	
}

?>
