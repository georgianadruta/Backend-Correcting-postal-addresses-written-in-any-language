package application.dataset.structure;

import application.dataset.storage.DataStorage;
import application.solution.SolutionUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data
public abstract class AbstractLocation implements Serializable {
    protected final int geoNameId;
    protected final String name;
    protected final String asciiName;
    protected final List<String> alternateNames;
    //    protected final float latitude;
//    protected final float longitude;
//    protected final String featureClass;
//    protected final String featureCode;
    protected final String code;
    //    protected final String cc2;
    protected final String admin1;
    //    protected final String admin2;
//    protected final String admin3;
//    protected final String admin4;
//    protected final float population;
//    protected final String deviation;
//    protected final String dem;
//    protected final String timezone;
//    protected final String modificationDate;
    protected Set<AbstractLocation> subRegions;

    public abstract AbstractLocation getRoot();

    public AbstractLocation(int geoNameId, String name, String asciiName, String[] alternateNames, String code, String admin1) {
//                            float latitude, float longitude, String featureClass, String featureCode, String code, String cc2, String admin1,
//                            String admin2, String admin3, String admin4, float population, String deviation, String dem, String timezone, String modificationDate) {
        this.geoNameId = geoNameId;
        this.name = name;
        this.asciiName = asciiName;
        this.alternateNames = List.of(alternateNames);
        this.code = code;
        this.admin1 = admin1;
//        this.latitude = latitude;
//        this.longitude = longitude;
//        this.featureClass = featureClass;
//        this.featureCode = featureCode;
//        this.code = code;
//        this.cc2 = cc2;
//        this.admin1 = admin1;
//        this.admin2 = admin2;
//        this.admin3 = admin3;
//        this.admin4 = admin4;
//        this.population = population;
//        this.deviation = deviation;
//        this.dem = dem;
//        this.timezone = timezone;
//        this.modificationDate = modificationDate;
        this.subRegions = new HashSet<>();
    }

    /**
     * add subregion if it's not already added
     *
     * @param abstractLocation the given subregion to add
     */
    public void addSubRegion(AbstractLocation abstractLocation) {
        if (!DataStorage.foundGeoNameIds.contains(abstractLocation.geoNameId)) {
            subRegions.add(abstractLocation);
            DataStorage.foundGeoNameIds.add(geoNameId);
        }
    }

    public void addAlternateNamesInMap(String[] alternateNames, AbstractLocation parent) { // tarile au ca parinte valoarea null
        for (String alternateName : alternateNames) {
            SolutionUtil.multimap.put(alternateName, parent);
        }
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
//                ", latitude=" + latitude +
//                ", longitude=" + longitude +
//                ", featureClass='" + featureClass + '\'' +
//                ", featureCode='" + featureCode + '\'' +
                ", code='" + code + '\'' +
//                ", cc2='" + cc2 + '\'' +
//                ", admin1='" + admin1 + '\'' +
//                ", admin2='" + admin2 + '\'' +
//                ", admin3='" + admin3 + '\'' +
//                ", admin4='" + admin4 + '\'' +
//                ", population=" + population +
//                ", deviation='" + deviation + '\'' +
//                ", dem='" + dem + '\'' +
//                ", timezone='" + timezone + '\'' +
//                ", modificationDate='" + modificationDate + '\'' +
                ", subRegions=" + subRegions +
                '}';
    }
}
