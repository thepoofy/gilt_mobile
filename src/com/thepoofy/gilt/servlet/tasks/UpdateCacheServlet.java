/**
 *
 */
package com.thepoofy.gilt.servlet.tasks;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.thepoofy.gilt.GiltProperty;
import com.thepoofy.gilt.servlet.ServletBase;

/**
 * @author Willum
 *
 */
@SuppressWarnings("serial")
public class UpdateCacheServlet extends ServletBase
{
//	private static final Logger log = Logger.getLogger(UpdateCacheServlet.class.getName());

	protected void handleResponse(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException
	{
		try
		{
			for(GiltProperty prop : GiltProperty.values())
			{
				Queue queue = QueueFactory.getDefaultQueue();
				queue.add(TaskOptions.Builder.withUrl("/tasks/update_sale").param("prop", prop.name()));
			}
			
			doResponse(true, response);
		}
		catch(Throwable t)
		{
			doError(t, response);
		}
	}
}
