package dog.rescue.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import dog.rescue.controller.model.LocationData;
import dog.rescue.entity.Location;

public class RescueServiceTestSupport {
	
	private static final String LOCATION_TABLE = "location";
	//@formatter:off
	
	private LocationData insertAddress1 = new LocationData(
			1L,
			"North Hills Dog Rescue Society",
			"52 Pine Street",
			"Abdington",
			"Maryland",
			"21009",
			"(410) 459-3200"
	);
	private LocationData insertAddress2 = new LocationData(
			2L,
			"Navarre Rescue",
			"42 Valley Farms Street",
			"Navarre",
			"Florida",
			"32556",
			"(850) 204-9485"
	);
			
	
	//@formatter:on
	
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected RescueController rescueController;
	
	protected LocationData buildInsertLocation(int which) {
		return which == 1 ? insertAddress1 : insertAddress2;
	}

	protected LocationData insertLocation(LocationData locationData) {
		Location location = locationData.toLocation();
		LocationData clone = new LocationData(location);
		
		clone.setLocationId(null);
		return rescueController.createLocation(clone);
	}
	
	protected int rowsInLocationTable() {
		return JdbcTestUtils.countRowsInTable(jdbcTemplate, LOCATION_TABLE);
	}
	
	protected LocationData retrieveLocation(Long locationId) {
		return rescueController.retrieveLocation(locationId);
	}
	
	protected List<LocationData> insertTwoLocations() {
		LocationData location1 = insertLocation(buildInsertLocation(1));
		LocationData location2 = insertLocation(buildInsertLocation(2));
		
		return List.of(location1, location2);
	}
	
	protected List<LocationData> retrieveAllLocations() {
		return rescueController.retrieveAllLocations();
	}
	
	protected List<LocationData> sorted(List<LocationData> list) {
		List<LocationData> data = new LinkedList<>(list);
		
	data.sort(
			(loc1, loc2) -> (int)(loc1.getLocationId() - loc2.getLocationId()));
		
	return data;
	}

}
