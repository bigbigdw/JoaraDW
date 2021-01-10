package Bigbigdw.JoaraDW.Test;

import java.util.ArrayList;

public class TestData {
    ArrayList<Test_ListData> items = new ArrayList<>();

    public ArrayList<Test_ListData> getData() {

        Test_ListData data = new Test_ListData("https://cf.joara.com/banner_file/20201231_134312.jpg");
        items.add(data);
        data = new Test_ListData("https://cf.joara.com/banner_file/20201231_134312.jpg");
        items.add(data);
        data = new Test_ListData("https://cf.joara.com/banner_file/20201231_134312.jpg");
        items.add(data);

        return items;
    }

}
