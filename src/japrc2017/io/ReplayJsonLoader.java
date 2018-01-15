package japrc2017.io;

/*
 * ExamNumber : Y3851551
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import japrc2017.util.ReplayEvenRecordJsonUtil;
import japrc2017.util.TickEventReplay;

public class ReplayJsonLoader {
	private List<TickEventReplay> replayEventList;
	public List<TickEventReplay> getReplayEventList() {
		return replayEventList;
	}
	private BufferedReader reader;
	
	public ReplayJsonLoader(InputStream data) {
		this.reader = new BufferedReader(new InputStreamReader(data));
		replayEventList = new ArrayList<TickEventReplay> ();
	}
	
	public void initializeJsonData() {
		String line = "",result="";
		try {
			while((line=reader.readLine())!=null){
				result += line;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONArray jsonArray = new JSONArray(result);
		for(int i = 0 ; i< jsonArray.length() ; i++) {
			replayEventList.add(ReplayEvenRecordJsonUtil.getFormatTickEventReplay(jsonArray.getJSONObject(i)));
		}
	}
	
	public void closeAll() {
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
