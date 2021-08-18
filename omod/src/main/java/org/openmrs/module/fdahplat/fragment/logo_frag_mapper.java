package org.openmrs.module.fdahplat.fragment;


package org.openmrs.ui.framework.fragment;

/**
 * Maps a request to a fragment e.g. you might map the request for the 'header' fragment in the
 * 'appui' to a different fragment 'myheader' in 'mymodule'
 */
public class logo_frag_mapper implements interface FragmentRequestMapper {
	protected final Log log = LogFactory.getLog(getClass());
	/**
	 * Implementations should call {@link FragmentRequest#setProviderNameOverride(String)} and
	 * {@link FragmentRequest#setFragmentIdOverride(String)}, and return true if they want to remap a
	 * request, or return false if they didn't remap it.
	 * 
	 * @param request may have its providerNameOverride and fragmentIdOverride set
	 * @return true if this fragment was mapped (by overriding the provider and/or fragment), false
	 *         otherwise
	 */
	boolean mapRequest(FragmentRequest request){
		System.out.println("NewlogoautoHeaderFragment: " + request.getProviderName() + "  FRAGMENT: "
		        + request.getFragmentId());
		log.info(request.toString());
		if (request.getProviderName().equals("fdahplat")) {
			if (request.getFragmentId().equals("header")) {
				// change to the custom login provided by the module
				request.setProviderNameOverride("fdahplat");
				request.setFragmentIdOverride("demoHeader"); // no need to specify this if the name of the fragment is the same as the one being over
				log.info(request.toString());
				return true;
			}
		}
		return false;
	}
	
	
}
