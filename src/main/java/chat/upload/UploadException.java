package chat.upload;

public class UploadException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1614439865949764318L;

	public UploadException (Throwable cause) {
		super(cause);
	}

	public UploadException (String message, Throwable cause) {
		super(message, cause);
	}
	
	public UploadException (String message) {
		super(message);
	}

}
