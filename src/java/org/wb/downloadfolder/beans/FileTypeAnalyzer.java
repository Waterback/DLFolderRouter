/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.wb.downloadfolder.beans;

import java.io.File;
import java.util.Map;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 *
 * @author martinh
 */
public class FileTypeAnalyzer implements Processor {

    private Map<String,String> fileTypes;

    @Override
    public void process(Exchange exchng) throws Exception {

        String filename = exchng.getIn().getHeader("CamelFileName", String.class);
        System.out.println("Filename:" + filename);
        int ind = filename.lastIndexOf(".");
        if (ind >= 0) {
            String identifier = filename.substring(ind+1);
            String filetype = fileTypes.get(identifier); 
            if (filetype == null || filetype.isEmpty())
                filetype = "undone";
            System.out.println("filetype:" + filetype);
            exchng.getIn().setHeader("SubDirectionHint", filetype);
        }

    }

    public void setFileTypes(Map<String, String> fileTypes) {
        this.fileTypes = fileTypes;
    }
    
    


}
