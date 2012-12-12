package kfc.mykfchotshot.db;

import java.util.List;

public class SortAlgo {
	public static List<Stock> srtStockName(List<Stock> list) {
		int temp=0;
		for(int i=0;i<list.size()-1;i++){
			temp=i;
			for(int j=i;j<list.size();j++){
				if(list.get(i).getName().compareTo(list.get(j).getName())>0)
					temp=j;
			}
			if(temp!=i){
				Stock s=list.get(i);
				list.set(i, list.get(temp));
				list.set(temp, s);
			}
		}
		return list;
	}
	
	public static List<Stock> srtStockQty(List<Stock> list) {
		int temp=0;
		for(int i=0;i<list.size()-1;i++){
			temp=i;
			for(int j=i;j<list.size();j++){
				if(Integer.parseInt(list.get(i).getQty())>Integer.parseInt(list.get(j).getQty()))
					temp=j;
			}
			if(temp!=i){
				Stock s=list.get(i);
				list.set(i, list.get(temp));
				list.set(temp, s);
			}
		}
		return list;
	}
	
	public static List<Stock> srtStockDate(List<Stock> list) {
		int temp=0;
		for(int i=0;i<list.size()-1;i++){
			temp=i;
			for(int j=i;j<list.size();j++){
				if(list.get(i).getDate().before(list.get(j).getDate()))
					temp=j;
			}
			if(temp!=i){
				Stock s=list.get(i);
				list.set(i, list.get(temp));
				list.set(temp, s);
			}
		}
		return list;
	}
	
	public static List<Stock> srtStockExp(List<Stock> list) {
		int temp=0;
		for(int i=0;i<list.size()-1;i++){
			temp=i;
			for(int j=i;j<list.size();j++){
				if(list.get(i).getExp().before(list.get(j).getExp()))
					temp=j;
			}
			if(temp!=i){
				Stock s=list.get(i);
				list.set(i, list.get(temp));
				list.set(temp, s);
			}
		}
		return list;
	}
	
	public static List<Stock> srtStockSold(List<Stock> list) {
		int temp=0;
		for(int i=0;i<list.size()-1;i++){
			temp=i;
			for(int j=i;j<list.size();j++){
				if(list.get(i).getTPrice()<list.get(j).getTPrice())
					temp=j;
			}
			if(temp!=i){
				Stock s=list.get(i);
				list.set(i, list.get(temp));
				list.set(temp, s);
			}
		}
		return list;
	}
}
