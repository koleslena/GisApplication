package ru.koleslena.gis.model

import ru.koleslena.gis.Utils

case class AptekaDistance  (
	name: String,
	address: String,
	distance: Double
	) extends Ordered[AptekaDistance] {
  
  	def compare(other: AptekaDistance) = {
		( this.distance - other.distance).toInt
  	}
  	
  	override def toString: String = {
		name + Utils.delimeter + address + Utils.delimeter + distance
  	}
}