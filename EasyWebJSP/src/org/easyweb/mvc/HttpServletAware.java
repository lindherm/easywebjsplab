package org.easyweb.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HttpServletAware {
	void setHttpRequest(HttpServletRequest request);

	void setHttpResponse(HttpServletResponse response);
}
