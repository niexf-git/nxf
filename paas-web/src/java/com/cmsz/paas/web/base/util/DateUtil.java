package com.cmsz.paas.web.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;

import com.cmsz.paas.web.base.exception.PaasWebException;

public class DateUtil {
    public static String pattenDate = "yyyy-MM-dd";
    public static String pattenMonth = "yyyy-MM";
    public static String pattenTimeH = "HH";
    public static String pattenTimeHM = "HHmm";
    public static String pattenTimeHMS = "HHmmss";//0-23;
    public static String pattenTimestamp = "yyyyMMdd HHmmss";

    public static final String[] DATE_PATTERNS = {
    	"yyyy-MM-dd HH:mm:ss",
    	"yyyy/MM/dd HH:mm:ss",
    	"MM/dd/yyyy HH:mm:ss",
    	"yyyy-MM-dd HH:mm",
    	"yyyy/MM/dd HH:mm",
    	"yyyyMMdd HHmmss",
    	"yyyy-MM-dd",
    	"yyyy/MM/dd",
    	"MM/dd/yyyy"
    };

    public static boolean checkDate( String dateStr ){
        return checkDate( dateStr, pattenDate );
    }

    public static boolean checkDate( String dateStr, String patten ){
        if( dateStr == null || dateStr.equals( "" ) == true ){
            return false;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(patten);
            java.util.Date utilDate = sdf.parse(dateStr, new java.text.ParsePosition(0));
            if( utilDate == null ){
                return false;
            }else{
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
    }

    public static boolean checkTimeHM( String timeStr ){
        if(timeStr.compareTo("23:59")>0){
            return false;
        }
        return checkDate( timeStr, pattenTimeHM );
    }

    public static boolean checkTimeHM( String timeStr,String pattenTime){
        if(timeStr.compareTo("23:59")>0){
            return false;
        }
        return checkDate(timeStr,pattenTime);
    }

    public static Integer compareDate( java.sql.Date dateLeft,java.sql.Date dateRight ){
        return dateLeft.compareTo( dateRight );
    }
    /**
     * dateLeft < dateRight   return <0
     * dateLeft = dateRight   return 0
     * dateLeft > dateRight   return >0
     * @param dateLeft Date
     * @param dateRight Date
     * @return Integer
     */
    public static Integer compareDate( java.util.Date dateLeft,java.util.Date dateRight ){
        return dateLeft.compareTo( dateRight );
    }
    public static Integer compareDate( String dateLeft,String dateRight ){
        return compareDate( dateLeft, dateRight, null );
    }

    public static Integer compareDate( String dateLeft,String dateRight, String patten ){
        java.util.Date leftDate=toDate(dateLeft, patten);
        java.util.Date rightDate=toDate(dateRight, patten);
        if(leftDate!=null && rightDate!=null) {
            return leftDate.compareTo(rightDate);
        }
        throw new RuntimeException("Can't compare null date : dateLeft=["+dateLeft+"] , dateRight=["+dateRight+"] ,pattern="+patten);
    }


    /**
     * ���ִ�ת�ɱ�׼��ʱ���ִ���HH:mm�����,
     * NUll����""����""��
     * ������ִ����޷����?ת��Ϊ"00:00"���
     * @author Lu Liang
     * @param HHMM String
     * @return String
     */
    public static String convertNumToTime(String HHMM) {
      String standardTimeStr = "";
      if(HHMM != null && HHMM.equals("") == false){
        Integer iHH = 0;
        Integer iMM = 0;
        java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
        nf.setMinimumIntegerDigits(2);
        nf.setMaximumIntegerDigits(2);
        try {
          Integer iHHMM = Integer.parseInt(HHMM);
          if (HHMM.length() <= 2) {
            iHH = iHHMM;
          } else if (HHMM.length() == 3) {
            iHH = Integer.parseInt(HHMM.substring(0, 2));
            iMM = Integer.parseInt(HHMM.substring(2));
          } else if (HHMM.length() >= 4) {
            iHH = Integer.parseInt(HHMM.substring(0, 2));
            iMM = Integer.parseInt(HHMM.substring(2, 4));
          }
        } catch (NumberFormatException ex) {
          String[] timeStrings = HHMM.split(":");
          if (timeStrings.length >= 2) {
            try {
              iHH = Integer.parseInt(timeStrings[0]);
              iMM = Integer.parseInt(timeStrings[1]);
            } catch (NumberFormatException ex1) {
              System.out.println("It's NOT a Time String at all!");
            }
          }
        } finally {
          if (iHH >= 24) {
            iHH = iHH % 24;
          }
          if (iMM >= 60) {
            iMM = iMM % 60;
          }
          standardTimeStr = nf.format(iHH) + ":" + nf.format(iMM);
          if (standardTimeStr.length() == 5 && checkTimeHM(standardTimeStr, "HH:mm") == false) {
            standardTimeStr = "";
          }
        }
      }
      return standardTimeStr;
    }

    //Ĭ�ϵ����ڸ�ʽΪpattenDate = "yyyy-MM-dd"
    public static String dateToString(java.sql.Date date){
        return dateToString(date,pattenDate);
    }

    //����patten��ʽת��
    public static String dateToString(java.sql.Date date,String patten){
        if(date == null || patten == null) {
			return "";
		}
        SimpleDateFormat df = new SimpleDateFormat(patten);
        return df.format(date);
    }

    //Ĭ�ϵ����ڸ�ʽΪpattenDate = "yyyy-MM-dd"
    public static String dateToString(java.util.Date date) {
        return dateToString(date, pattenDate);
    }



    //����patten��ʽת��
    public static String dateToString(java.util.Date date, String patten) {
        if(date == null || patten == null) {
			return "";
		}
        SimpleDateFormat df = new SimpleDateFormat(patten);
        return df.format(date);
    }

    //Ĭ�ϵ�timestamp��ʽΪ"yyyy-MM-dd HH:mm:ss"
      public static String formatSqlTimestamp(java.util.Date sqlTimestamp) {
        return formatSqlTimestamp(sqlTimestamp, pattenTimestamp);
      }


    //���ִ��õ�java.sql.Timestamp����
      public static String formatSqlTimestamp(java.util.Date sqlTimestamp, String patten) {
        java.util.Date utilDate = sqlTimestamp;
        SimpleDateFormat sdf = new SimpleDateFormat(patten);
        StringBuffer dateSb = new StringBuffer();
        sdf.format(utilDate, dateSb, new java.text.FieldPosition(0));
        return dateSb.toString();
      }

    /**
       * ��ȡ��ֹ��֮������������ֹʱ����ͬ����������1
       * @param Start Calendar
       * @param End Calendar
       * @return Integer
       */
      public static Integer getBetweenDays(Calendar Start, Calendar End) {
        Calendar cStart = (Calendar) Start.clone();
        cStart.set(Calendar.HOUR, 0);
        cStart.set(Calendar.MINUTE, 0);
        cStart.set(Calendar.SECOND, 0);
        cStart.set(Calendar.MILLISECOND, 0);
        cStart.set(Calendar.AM_PM, Calendar.AM);

        Calendar cEnd = (Calendar) End.clone();
        cEnd.set(Calendar.HOUR, 0);
        cEnd.set(Calendar.MINUTE, 0);
        cEnd.set(Calendar.SECOND, 0);
        cEnd.set(Calendar.MILLISECOND, 0);
        cEnd.set(Calendar.AM_PM, Calendar.AM);

        long lRet = cEnd.getTimeInMillis() - cStart.getTimeInMillis();
        int iRet = (int) (lRet/( 24 * 60 * 60 * 1000 ));
        iRet ++;

        return iRet;
    }


    public static Integer getCurrMonth() {
        return Calendar.getInstance().get(Calendar.MONTH);
    }

    public static Integer getCurrYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static String getSysDate() {
		return dateToString(new java.util.Date(),"yyyy-MM-dd");
    }

    /**
     * �ĸ�����toSqlDate, toSqlDate(), toSqlTimestamp, toSqlTimestamp����java.sql��Ķ���
     * ��toDate, toDate, toTimestamp, toTimestamp��Ӧ
     * ��һ�㲻ͬ����:java.util.Date�����ں�time��Ϣ,��java.sql.Dateֻ��������Ϣ,����time��Ϣ.
     * java.sql.Timestamp�����ں�time��Ϣ.
     * @param dateStr String
     * @param patten String
     * @return Date
     */

    public static String getYYYYMM(Integer year, Integer month){
        return year.toString()+ ((month+1) < 10 ? "0"+(month+1) : month +1);
    }

      public static Date minusMin(Date date, Integer minToMinus){
		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.MINUTE,-minToMinus);
		return ca.getTime();
	}

      public static Date plusDays(Date date, Integer dayToAdd) {

		DateTime dt = new DateTime(date);
		dt = dt.plusDays(dayToAdd);
		return dt.toDate();
	}

      public static Date plusMonths(Date date, Integer monthToAdd) {

		DateTime dt = new DateTime(date);
		dt = dt.plusMonths(monthToAdd);
		return dt.toDate();
	}

      //Ĭ�ϵ����ڸ�ʽΪpattenTimeHMS = "HH:mm:ss"
    public static String timeToString(java.sql.Date date) {
        return dateToString(date, pattenTimeHMS);
    }

      //Ĭ�ϵ����ڸ�ʽΪpattenDate = "yyyy-MM-dd"
    public static java.util.Date toDate(String dateStr) {
        if(dateStr==null || dateStr.trim().equals("")) {
            return null;
        }

        String dateValue=dateStr.trim();

        for(String pattern:DATE_PATTERNS) {
        	if(pattern.length()==dateValue.length()) {
        		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                try {
					java.util.Date utilDate = sdf.parse(dateValue);
					return utilDate;
				} catch (ParseException e) {

				}

        	}
        }

		throw new PaasWebException("Incorrect date format: " + dateValue);
    }

    //patten�����ֻ�����ڸ�ʽ,��Ҫ��time��ʽ
    //����һ����������Ϣ��java.util.Date����, ��time��ϢΪ0
    public static java.util.Date toDate(String dateStr, String patten) {
        if(patten==null || patten.trim().equals("")) {
            return toDate(dateStr);
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(patten);
            java.util.Date utilDate = sdf.parse(dateStr, new java.text.ParsePosition(0));
            return utilDate;
        }
    }

    public static java.sql.Date toSQLDate(String dateStr) {
        return toSQLDate(dateStr, pattenDate);
    }

    //patten�����ֻ�����ڸ�ʽ,��Ҫ��time��ʽ
    //����һ����������Ϣ��java.util.Date����, ��time��ϢΪ0
    public static java.sql.Date toSQLDate(String dateStr, String patten) {
        SimpleDateFormat sdf = new SimpleDateFormat(patten);
        java.util.Date utilDate = sdf.parse(dateStr, new java.text.ParsePosition(0));
        if(utilDate!=null){
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            return sqlDate;
        }
        return null;
    }

    public static java.sql.Timestamp toSqlTimestamp(String timestampStr) {
        return toSqlTimestamp(timestampStr, pattenTimestamp);
      }

	public static java.sql.Timestamp toSqlTimestamp(String timestampStr, String patten) {
        java.util.Date utilDate = toTimestamp(timestampStr, patten);
        if (utilDate == null) {
          return null;
        } else {
          return new java.sql.Timestamp(utilDate.getTime());
        }
      }

	//Ĭ�ϵ�timeStamp��ʽΪpattenTimestamp = "yyyy-MM-dd HH:mm:ss"
    public static java.util.Date toTimestamp(String timestampStr) {
        return toTimestamp( timestampStr, pattenTimestamp );
    }
	
	//patten��ú����ڸ�ʽ��time��ʽ
    //����һ����������Ϣ��time��Ϣ��java.util.Date����
    public static java.util.Date toTimestamp(String timestampStr, String patten) {
        return toDate( timestampStr, patten );
    }

}

