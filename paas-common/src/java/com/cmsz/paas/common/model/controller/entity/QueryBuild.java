package com.cmsz.paas.common.model.controller.entity;

import java.util.List;

public class QueryBuild {
				
		private List<CodePathEntity> codePath;
		
		private String dockerFile;
		
		private String cmd;
		
		private String imageName;
		
		private String imageVersion;
		
		private int isChoise;
		
		private int isUnitTest; //1勾选；0不勾选
		
		private int status;
		
		private double time;
		
		private String lineCoverage;//行覆盖率,返回给前台 
	   //单元测试部分
		/**
		 * 测试报告目录
		 */
        private String uniTeRepDir;
        /**
         * 测试报告入口文件
         */
        private String uniTeRepFile;
        /**
         * 覆盖率文件目录
         */
        private String covFileDir;
        /**
         * 覆盖率入口文件
         */
        private String covFile;

		
		public List<CodePathEntity> getCodePath()
        {
            return codePath;
        }

        public void setCodePath(List<CodePathEntity> codePath)
        {
            this.codePath = codePath;
        }

        public String getDockerFile()
        {
            return dockerFile;
        }

        public void setDockerFile(String dockerFile)
        {
            this.dockerFile = dockerFile;
        }

        public String getCmd() {
			return cmd;
		}

		public void setCmd(String cmd) {
			this.cmd = cmd;
		}

		public String getImageName() {
			return imageName;
		}

		public void setImageName(String imageName) {
			this.imageName = imageName;
		}

		public String getImageVersion() {
			return imageVersion;
		}

		
		public int getIsUnitTest()
        {
            return isUnitTest;
        }

        public void setIsUnitTest(int isUnitTest)
        {
            this.isUnitTest = isUnitTest;
        }

        public void setImageVersion(String imageVersion) {
			this.imageVersion = imageVersion;
		}

		public int getIsChoise() {
			return isChoise;
		}

		public void setIsChoise(int isChoise) {
			this.isChoise = isChoise;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public double getTime() {
			return time;
		}

		public void setTime(double time) {
			this.time = time;
		}

        public String getUniTeRepDir()
        {
            return uniTeRepDir;
        }

        public void setUniTeRepDir(String uniTeRepDir)
        {
            this.uniTeRepDir = uniTeRepDir;
        }

        public String getUniTeRepFile()
        {
            return uniTeRepFile;
        }

        public void setUniTeRepFile(String uniTeRepFile)
        {
            this.uniTeRepFile = uniTeRepFile;
        }

        public String getCovFileDir()
        {
            return covFileDir;
        }

        public void setCovFileDir(String covFileDir)
        {
            this.covFileDir = covFileDir;
        }

        public String getCovFile()
        {
            return covFile;
        }

        public void setCovFile(String covFile)
        {
            this.covFile = covFile;
        }

        @Override
        public String toString()
        {
            return "QueryBuild [codePath=" + codePath + ", dockerFile=" + dockerFile + ", cmd="
                   + cmd + ", imageName=" + imageName + ", imageVersion=" + imageVersion
                   + ", isChoise=" + isChoise + ", status=" + status + ", time=" + time
                   + ", uniTeRepDir=" + uniTeRepDir + ", uniTeRepFile=" + uniTeRepFile
                   + ", covFileDir=" + covFileDir + ", covFile=" + covFile + "]";
        }

		public String getLineCoverage() {
			return lineCoverage;
		}

		public void setLineCoverage(String lineCoverage) {
			this.lineCoverage = lineCoverage;
		}

		 

	
}
