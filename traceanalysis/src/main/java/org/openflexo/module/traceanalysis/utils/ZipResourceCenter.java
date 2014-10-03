/*
 * (c) Copyright 2010-2011 AgileBirds
 *
 * This file is part of OpenFlexo.
 *
 * OpenFlexo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * OpenFlexo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenFlexo. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.openflexo.module.traceanalysis.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.openflexo.foundation.resource.DirectoryResourceCenter;
import org.openflexo.foundation.resource.FlexoFileResource;
import org.openflexo.toolbox.IProgress;
import org.openflexo.toolbox.ToolBox;

/**
 * Temporary utilities to add automatically the depart viewpoint.
 * Should be changed.
 * @author Vincent
 *
 */
public class ZipResourceCenter extends DirectoryResourceCenter {

	public ZipResourceCenter(File resourceCenterDirectory) {
		super(resourceCenterDirectory);
		
	}

	public static ZipResourceCenter instanciateNewZipResourceCenter(File zip) {
		logger.info("Instanciate ResourceCenter from " + zip.getAbsolutePath());
		ZipResourceCenter zipResourceCenter = null;
		if(isZipFile(zip)){
			try {
				File target = new File(zip.getParentFile()+"/"+zip.getName().substring(0, zip.getName().lastIndexOf(".")));
				if(!target.exists()){
					unzip(zip, target);	
				}
				zipResourceCenter = new ZipResourceCenter(target);
				zipResourceCenter.update();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return zipResourceCenter;
	}
	
	public static void unzip(File zipfile, File folder) throws FileNotFoundException, IOException{

        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipfile.getCanonicalFile())));
        try {
            ZipEntry ze = null;
            while((ze = zis.getNextEntry()) != null){
                File f = new File(folder.getCanonicalPath(), ze.getName());
           
                if (ze.isDirectory()) {
                    f.mkdirs();
                    continue;
                }

                f.getParentFile().mkdirs();
                OutputStream fos = new BufferedOutputStream(new FileOutputStream(f));

                try {
                    try {
                        final byte[] buf = new byte[8192];
                        int bytesRead;
                        while (-1 != (bytesRead = zis.read(buf)))
                            fos.write(buf, 0, bytesRead);
                    }
                    finally {
                        fos.close();
                    }
                }
                catch (final IOException ioe) {
                    f.delete();
                    throw ioe;
                }
            }
        }
        finally {
            zis.close();
        }
    }
	
	private static boolean isZipFile(File zip){
		return (zip.getName().endsWith(".zip")
				|| zip.getName().endsWith(".jar")
				|| zip.getName().endsWith(".tar"));
	}

	public static List<File> getClassPathFiles(){
		List<File> files = null;
    	files = new ArrayList<File>();
    	/*String delim = ":";
    	if(ToolBox.isWindows()){
    		delim = ";";
    	}else if(ToolBox.isLinux()){
    		delim = ":";
    	}*/
		StringTokenizer string = new StringTokenizer(System.getProperty("java.class.path"), Character.toString(File.pathSeparatorChar));
		while(string.hasMoreTokens())
		{
			files.add(new File(string.nextToken()));
		}
    	return files;
	}

	public static File getClassPathFile(String name){
		for(File file : getClassPathFiles()){
			if(file.getName().endsWith(name)){
				return file;
			}
		}
		return null;
	}
	
	@Override
	public Collection<FlexoFileResource<?>> getAllResources(IProgress progress) {
		return getAllResources();
	}
	
	
}
