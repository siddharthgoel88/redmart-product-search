import java.net.UnknownHostException;

import play.GlobalSettings;
import play.Logger;

import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;

import controllers.MorphiaObject;

public class Global extends GlobalSettings {

	private char[] password = {'1', '2', '3', '4', '5'};
	
	@Override
	public void onStart(play.Application arg0) {
		super.beforeStart(arg0);
		Logger.debug("** onStart **"); 
		try {
			MorphiaObject.mongo = new Mongo("localhost", 27017);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		MorphiaObject.morphia = new Morphia();
		MorphiaObject.datastore = MorphiaObject.morphia.createDatastore(MorphiaObject.mongo, "test", "redmart", password);
		MorphiaObject.datastore.ensureIndexes();   
		MorphiaObject.datastore.ensureCaps();

		Logger.debug("** Morphia datastore: " + MorphiaObject.datastore.getDB());
	}
}
