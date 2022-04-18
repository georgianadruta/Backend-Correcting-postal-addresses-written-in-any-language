package application.dataset.structure;

import application.dataset.storage.DataStorage;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * abstract class to allow creating functionality that subclasses can implement or override
 */
@Data
public abstract class AbstractLocation implements Serializable {
    protected final int geoNameId;
    protected final String name;
    protected final String asciiName;
    protected final List<String> alternateNames;
    protected final String code;
    protected final String admin1;
    protected Set<AbstractLocation> subRegions;

    public abstract AbstractLocation getRoot();

    public AbstractLocation(int geoNameId, String name, String asciiName, String[] alternateNames, String code, String admin1) {
        this.geoNameId = geoNameId;
        this.name = name;
        this.asciiName = asciiName;
        this.alternateNames = List.of(alternateNames);
        this.code = code;
        this.admin1 = admin1;
        this.subRegions = new HashSet<>();
    }

    /**
     * add subregion if it's not already added
     */
    public void addSubRegion(AbstractLocation abstractLocation) {
        if (!DataStorage.foundGeoNameIds.contains(abstractLocation.geoNameId)) {
            subRegions.add(abstractLocation);
            DataStorage.foundGeoNameIds.add(geoNameId);
        }
    }

    /**
     * helpful method to check if it exists at least one element with given name in subregion list for current location
     */
    public boolean contains(String locationName) {
        for (AbstractLocation abstractLocation : subRegions) {
            if (abstractLocation.getName().equals(locationName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractLocation location = (AbstractLocation) o;
        return geoNameId == location.geoNameId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(geoNameId);
    }

    @Override
    public String toString() {
        return "AbstractLocation{" +
                "geoNameId=" + geoNameId +
                ", name='" + name + '\'' +
                ", asciiName='" + asciiName + '\'' +
                ", alternateNames=" + alternateNames +
                ", code='" + code + '\'' +
                ", subRegions=" + subRegions +
                '}';
    }
}
