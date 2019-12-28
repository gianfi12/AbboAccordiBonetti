package com.SafeStreets.mapsserviceadapter;

import com.SafeStreets.model.Coordinate;
import com.SafeStreets.model.Place;
import com.google.maps.GeoApiContext;


/**
 * An implementation of {@link MapsServiceInterface} that uses Google Maps.
 *
 * @author Abbo Giulio A.
 * @see MapsServiceInterface
 */
class MapsServiceAdapter implements MapsServiceInterface {
    /**
     * The context of the maps service: must be a singleton.
     */
    private static final GeoApiContext context = new GeoApiContext
            .Builder()
            .apiKey("AIzaSyD6-YCMxksVl0uOV52UD6NJr-RuaUpPqbo")
            .build();

    /**
     * The constructor is hidden outside the package.
     * Use {@link MapsServiceInterface#getInstance()}.
     */
    MapsServiceAdapter() {
    }

/*
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MapsServiceAdapter adapter = new MapsServiceAdapter();
        while (true) {
            String s = sc.nextLine();
            try {
                adapter.geocoding(s);
            } catch (MapsServiceException.GeocodeException e) {
                e.printStackTrace();
            }
        }
    }
*/

    /**
     * {@inheritDoc}
     */
    @Override
    public Place reverseGeocoding(Place place) throws MapsServiceException.GeocodeException {
        //TODO(giulio): implement reverseGeocoding.
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Place geocoding(String location) throws MapsServiceException.GeocodeException {
        //TODO(giulio): implement geocoding.
        throw new UnsupportedOperationException();
        /*
        try {
            GeocodingResult[] results = GeocodingApi
                    .geocode(context, location)
                    .await();
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            System.out.println(gson.toJson(results[0].addressComponents));
        } catch (ApiException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return null;
        */
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Place geocoding(Coordinate coordinates) throws MapsServiceException.GeocodeException {
        //TODO(giulio): implement geocoding.
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPlaceInLocation(Place place, String location) throws MapsServiceException.GeocodeException {
        //TODO(giulio): implement isPlaceInLocation.
        throw new UnsupportedOperationException();
    }
}
