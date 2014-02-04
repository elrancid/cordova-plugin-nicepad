package it.nicefall.cordova.nicepad;

import it.nicefall.NicePad.App;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;
import org.json.JSONArray;
import org.json.JSONException;

public class NicePad extends CordovaPlugin {
	public static final String TAG = "NicePad";

	private App app;

	/**
	 * Constructor.
	 */
	public NicePad() {
	}

	/**
	 * Sets the context of the Command. This can then be used to do things like
	 * get file paths associated with the Activity.
	 *
	 * @param cordova The context of the main Activity.
	 * @param webView The CordovaWebView Cordova is running in.
	 */
	public void initialize( CordovaInterface cordova, CordovaWebView webView ) {
		super.initialize( cordova, webView );
		app = (App) cordova.getActivity().getApplicationContext();
	}

	/**
	 * Executes the request and returns PluginResult.
	 *
	 * @param action			The action to execute.
	 * @param args			  JSONArry of arguments for the plugin.
	 * @param callbackContext   The callback id used when calling back into JavaScript.
	 * @return				  True if the action was valid, false if not.
	 */
	public boolean execute( final String action, final JSONArray args, final CallbackContext callbackContext ) throws JSONException {
		android.util.Log.d( "[DEBUG]", "NicePad::execute(): " + action + " # " + args.toString() );	// TODO - remove
		if( action != null )
		{
			if (action.equals("connectionEvents")) {
				return connectionEvents(callbackContext);
			}
			else if( action.equals( "connect" ) ) {
				return connect(callbackContext);
				/*
				cordova.getActivity().runOnUiThread( new Runnable() { public void run() { ( (App) cordova.getActivity().getApplicationContext() ).doConnect(); } } );
				return true;
				 */
			}
			else if( action.equals( "disconnect" ) ) {
				return disconnect(callbackContext);
				/*
				cordova.getActivity().runOnUiThread( new Runnable() { public void run() { ( (App) cordova.getActivity().getApplicationContext() ).doDisconnect(); } } );
				return true;
				 */
			}
			else if( action.equals( "send" ) ) {
				if( args != null && args.length() > 0 ) {
					return this.send(callbackContext, args.getString(0));
					/*
					cordova.getActivity().runOnUiThread( new Runnable() { public void run() {
						if( ( (App) cordova.getActivity().getApplicationContext() ).doSendString( args.optString( 0, "" ) ) == 0 ) callbackContext.success( "ok" );
						else callbackContext.error( "Network error" );
					}});
					 */
				}
				else callbackContext.error( "String required" );
				return false;
			}
			else if( action.equals( "receive" ) ) {
				return this.receive(callbackContext);
				/*
				cordova.getActivity().runOnUiThread( new Runnable() { public void run() {
					if( ( (App) cordova.getActivity().getApplicationContext() ).doReceiveString() == 0 ) callbackContext.success( "ok" );
					else callbackContext.error( "Network error" );
				}});
				return true;
				 */
			}
			else if( action.equals( "exit" ) ) {
				return exit(callbackContext);
				/*
				cordova.getActivity().runOnUiThread( new Runnable() { public void run() { ( (App) cordova.getActivity().getApplicationContext() ).doExit(); } } );
				return true;
				 */
			}
		}
		return false;

		/*
		if( action.equals( "exit" ) )
		{
			android.util.Log.d( "[CordovaPlugin]", "EXIT !" );
		}
		else
		{
			return false;
		}
		return true;
		 */
	}

	public synchronized boolean connectionEvents(final CallbackContext callbackContext) {
		return app.doConnectionEvents(callbackContext);
	}
	
	public synchronized boolean send(final CallbackContext callbackContext, String message) {
		if ( app.doSend(message) ) {
			callbackContext.success();
			return true;
		}
		callbackContext.error("TCPClient not connected. Error sending message: "+message);
		return false;
	}

	public synchronized boolean receive(final CallbackContext callbackContext) {
		//return ( (App) cordova.getActivity().getApplicationContext() ).doReceive(callbackContext);
		return app.doReceive(callbackContext);
	}

	public synchronized boolean connect(final CallbackContext callbackContext) {
		/*
		cordova.getThreadPool().execute(new Runnable() {
			public void run() {
				( (App) cordova.getActivity().getApplicationContext() ).doConnect(callbackContext);
			}
		});
		*/
		return app.doConnect(callbackContext);
	}

	public synchronized boolean disconnect(final CallbackContext callbackContext) {
		/*
		cordova.getThreadPool().execute(new Runnable() {
			public void run() {
				( (App) cordova.getActivity().getApplicationContext() ).doDisconnect(callbackContext);
			}
		});
		*/
		return app.doDisconnect(callbackContext);
	}

	public boolean exit(final CallbackContext callbackContext) {
		/*
		cordova.getThreadPool().execute(new Runnable() {
			public void run() {
				( (App) cordova.getActivity().getApplicationContext() ).doExit();
			}
		});
		*/
		return app.doExit();
		/*
		boolean result = ( (App) cordova.getActivity().getApplicationContext() ).doExit();
		if (result) callbackContext.success();
		else callbackContext.error("Failed exiting.");
		return result;
		 */
	}
}