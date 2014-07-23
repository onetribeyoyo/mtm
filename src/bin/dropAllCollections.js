//
// drops all (non-system) collections in the database.
//
db.getCollectionNames().forEach(
    function(collectionName) {
        if (collectionName.substring(0, 7) == "system\.") {
            print( "keeping system collection: " + collectionName );
        } else {
            print( "dropping collection: " + collectionName );
            db.getCollection(collectionName).drop();
        }
    }
);
