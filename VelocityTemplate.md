# Velocity #

See [http://velocity.apache.org/](http://velocity.apache.org/)

# How to configure Velocity? #

## Manage null value ##

Create class NullValueInsertionEventHandler which implements ReferenceInsertionEventHandler from Velocity that implements the bahaviour of Velocity:

```
public class NullValueInsertionEventHandler implements ReferenceInsertionEventHandler {

	@Override
	public Object referenceInsert(String string, Object object) {
		if (object == null) {
			return "";
		}
		return object;
	}
}
```

Use NullValueInsertionEventHandler  like this :

```
IContext context = report.createContext();

EventCartridge eventCartridge = new EventCartridge();
eventCartridge.addEventHandler(new NullValueInsertionEventHandler());
eventCartridge.attachToContext((VelocityContext) context);
```