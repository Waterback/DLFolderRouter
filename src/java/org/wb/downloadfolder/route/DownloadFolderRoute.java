/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.wb.downloadfolder.route;

import java.util.Map;
import org.wb.downloadfolder.beans.FileTypeAnalyzer;
import org.apache.camel.spring.SpringRouteBuilder;

/**
 *
 * @author martinh
 */
public class DownloadFolderRoute extends SpringRouteBuilder{
    
    private Map<String,String> directions;
    
    @Override
    public void configure() throws Exception {

        FileTypeAnalyzer fta = lookup("filetypeAnalyzer", FileTypeAnalyzer.class);
        
        from("file://{{org.wb.dlf.dl}}?delete=true&readLock=rename&delay=500")
//                .autoStartup(false)
                .process(fta)
                .choice()
                    .when(header("SubDirectionHint").isEqualTo("sw"))
                        .to("{{org.wb.dlf.ep.sw}}")
                    .when(header("SubDirectionHint").isEqualTo("movies"))
                        .to("{{org.wb.dlf.ep.movies}}")
                    .when(header("SubDirectionHint").isEqualTo("music"))
                        .to("{{org.wb.dlf.ep.music}}")
                    .when(header("SubDirectionHint").isEqualTo("docu"))
                        .to("{{org.wb.dlf.ep.docu}}")
                    .when(header("SubDirectionHint").isEqualTo("undone"))
                        .to("{{org.wb.dlf.ep.undone}}")
                    .when(header("SubDirectionHint").isEqualTo("presentation"))
                        .to("{{org.wb.dlf.ep.presentation}}")
                    .when(header("SubDirectionHint").isEqualTo("application"))
                        .to("{{org.wb.dlf.ep.application}}")
                    .otherwise()
                        .to("{{org.wb.dlf.ep.undone}}");
                        

    }


}
