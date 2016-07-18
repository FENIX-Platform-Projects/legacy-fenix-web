/* Licence:
 *   Use this however/wherever you like, just don't blame me if it breaks anything.
 *
 * Credit:
 *   If you're nice, you'll leave this bit:
 *
 *   Class by Pierre-Alexandre Losson -- http://www.telio.be/blog
 *   email : plosson@users.sourceforge.net
 */
package org.fao.fenix.web.modules.core.server.upload;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.fileupload.disk.DiskFileItem;

/**
 * Created by IntelliJ IDEA.
 * 
 * @author Original : plosson on 05-janv.-2006 10:46:33 - Last modified by $Author: plosson $ on $Date: 2006/01/05
 *         10:09:38 $
 * @version 1.0 - Rev. $Revision: 1.1 $
 */
public class MonitoredDiskFileItem extends DiskFileItem {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private MonitoredOutputStream mos = null;
    private OutputStreamListener listener;

    public MonitoredDiskFileItem(String fieldName, String contentType, boolean isFormField, String fileName,
            int sizeThreshold, File repository, OutputStreamListener listener) {
        super(fieldName, contentType, isFormField, fileName, sizeThreshold, repository);
        this.listener = listener;
    }

    public OutputStream getOutputStream() throws IOException {
        if (mos == null) {
            mos = new MonitoredOutputStream(super.getOutputStream(), listener);
        }
        return mos;
    }
}