package com.example.trip_packer.Data;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.example.trip_packer.Database.RoomDB;
import com.example.trip_packer.Models.Items;
import com.example.trip_packer.constants.myconstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppData extends Application {

    RoomDB database;
    Context context;
    String category;

    public static final String LAST_VERSION = "LAST_VERSION";
    public static final int NEW_VERSION = 3;

    public AppData(RoomDB database) {
        this.database = database;
    }

    public AppData(RoomDB database, Context context) {
        this.database = database;
        this.context = context;

    }

    public List<Items> getBasicData() {
        category = "Basic Needs";
        List<Items> basicItem = new ArrayList<Items>();
        basicItem.clear();
        basicItem.add(new Items("Visa", category, false));
        basicItem.add(new Items("Passport", category, false));
        basicItem.add(new Items("Tickets", category, false));
        basicItem.add(new Items("Wallet", category, false));
        basicItem.add(new Items("Driving License", category, false));
        basicItem.add(new Items("Currency", category, false));
        basicItem.add(new Items("House key", category, false));
        basicItem.add(new Items("Book", category, false));
        basicItem.add(new Items("Travel pillow", category, false));
        basicItem.add(new Items("Eye patch", category, false));
        basicItem.add(new Items("Umbrella", category, false));
        basicItem.add(new Items("Note Book", category, false));

        return basicItem;
    }

    public List<Items> getPersonalcareData() {
        String[] data = {"Tooth-brush", "Tooth-paste", "Floss", "Mouthwash", "shaving cream", "Razor blade", "soap", "fiber", "shampoo", "hair conditioner", "Brush",
                "comb", "hair dryer", "curling iron", "hair moulder", "hair clip", "moisturizer", "lip cream", "contact lens", "perfume", "deodorant", "makeup remover", "wet wipes", "pad", "ear stick", "cotton", "nail polish", "nail polish  remover", "tweezers",
                "nail files", "suntan cream"};
        return prepareItemsList(myconstants.PERSONAL_CARE_CAMEL_CASE, data);
    }

    public List<Items> getClothingData() {
        String[] data = {"stockings", "underwear", "pajamas", "t-shirts", "casual dress", "evening dress", "shirt", "cardigan", "vest", "jacket", "skirt",
                "trousers", "jeans", "shorts", "suit", "coat", "rain coat", "glove", "hat", "scarf", "bikini", "belt", "slipper", "sneakers", "casual shoes", "heeled shoes", "sports wear"};
        return prepareItemsList(myconstants.CLOTHING_CAMEL_CASE, data);
    }

    public List<Items> getBabyNeedsData() {
        String[] data = {"snapsuit", "outfit", "jumpsuit", "baby shocks", "baby hat", "baby pyjamas", "baby bath towel", "muslin", "blanket", "dribble bibs", "baby laundry detergent",
                "baby bottles", "baby food thermos", "baby bottle brushes", "brest-feeding cover", "breast pump", "water bottle", "storage container", "baby food spoon", "highchairs", "diaper", "wet wipes", "baby cotton", "baby care cover", "baby shampoo", "baby soap", " baby nail scissors", "body moisturizer", "potty",
                "diaper rash cream", "serum physiological", "nasal aspirator", "fly repellent lotion", "pyrometer", "antipyretic syrup",
                "baby backpack", "probiotic power", "stroller", "baby carrier", "toys", "teether", "playpen", "baby radio", "non-slip sea shoes", "baby sunglasses"};
        return prepareItemsList(myconstants.BABY_NEEDS_CAMEL_CASE, data);
    }

    public List<Items> getHealthData() {
        String[] data = {"Aspirin", "drugs used", "vitamins used", "lens solutions", "condom", "hot water bag", "tincture of lodine", "adhesive plaster", "first aid kit", "replacement lens", "pain reliever",
                "fever reducer", "diarrhea stopper", "pain relieve spray"};
        return prepareItemsList(myconstants.HEALTH_CAMEL_CASE, data);
    }

    public List<Items> getTechnologyData() {
        String[] data = {"mobile phone", "phone cover", "E-book reader", "camera", "camera charger", "portable speaker", "ipad", "headphone", "laptop", "laptop charger", "mouse",
                "extension cable", "data transfer cable", "battery", "power bank", "DVD player", "flash-light", "MP3 player", "MP3 player charger", "phone charger", "voltage adapter", "SD card"
        };
        return prepareItemsList(myconstants.TECHNOLOGY_CAMEL_CASE, data);
    }

    public List<Items> getFoodData() {
        String[] data = {"snacks", "sandwich", "juice", "tea bags", "coffee", "water", "thermos", "chips", "baby food"};
        return prepareItemsList(myconstants.FOOD_CAMEL_CASE, data);
    }

    public List<Items> getBeachsuppliesData() {
        String[] data = {"sea glasses", "sea bed", "suntan cream", "beach umbrella", "swim fins", "beach slippers", "sunbed", "snorkel", "waterproof clock"};
        return prepareItemsList(myconstants.BEACH_SUPPLIES_CAMEL_CASE, data);
    }

    public List<Items> getcarsuppliesData() {
        String[] data = {"pump", "car jack", "spare car key", "accident record set", "auto refrigerator", "car cover", "car cahrger", "window sun shades"};
        return prepareItemsList(myconstants.CAR_SUPPLIES_CAMEL_CASE, data);
    }

    public List<Items> getNeedsData() {
        String[] data = {"Backpack", "daily bags", "laundry bag", "sewing kit", "travel lock", "luggage tag", "magazine", "sports equipment", "important numbers"};
        return prepareItemsList(myconstants.NEEDS_CAMEL_CASE, data);
    }


    public List<Items> prepareItemsList(String category, String[] data) {
        List<String> list = Arrays.asList(data);
        List<Items> dataList = new ArrayList<>();
        dataList.clear();
        for (int i = 0; i < list.size(); i++) {
            dataList.add(new Items(list.get(i), category, false));
        }
        return dataList;
    }

    public List<List<Items>> getAllData() {
        List<List<Items>> listofAllItems = new ArrayList<>();
        listofAllItems.clear();
        listofAllItems.add(getBasicData());
        listofAllItems.add(getClothingData());
        listofAllItems.add(getPersonalcareData());
        listofAllItems.add(getBabyNeedsData());
        listofAllItems.add(getHealthData());
        listofAllItems.add(getTechnologyData());
        listofAllItems.add(getFoodData());
        listofAllItems.add(getBeachsuppliesData());
        listofAllItems.add(getcarsuppliesData());
        listofAllItems.add(getNeedsData());
        return listofAllItems;

    }

    public void persistAllData() {
        List<List<Items>> listofAllItems = getAllData();
        for (List<Items> list : listofAllItems) {
            for (Items items : list) {
                database.mainDao().saveItem(items);
            }
        }
        System.out.println("DATA ADDED");
    }


    public void persistDataByCtaegory(String category,Boolean onlydelete)

    {
        try{
            List<Items>list=deleteAndGetlistByCatagory(category, onlydelete);
            if(!onlydelete)
            {
             for(Items item :list)
             {
                 database.mainDao().saveItem(item);
             }
                Toast.makeText(context, category+ "Reset successfully", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(context, category + "Reset successfully", Toast.LENGTH_SHORT).show();
            }

        }catch(Exception ex)
        {
            ex.printStackTrace();
            Toast.makeText(context, "something went worng", Toast.LENGTH_SHORT).show();
        }
    }
    private List<Items> deleteAndGetlistByCatagory(String category, Boolean onlyDelete) {
        if (onlyDelete) {
            database.mainDao().deleteAllCategoryAndAddedBy(category,myconstants.SYSTEM_SMALL);

        } else {
database.mainDao().deleteAllBycategory(category);
        }

        switch (category)
        {
            case myconstants.BASIC_NEEDS_CAMEL_CASE:
                return  getBasicData();

            case myconstants.CLOTHING_CAMEL_CASE:
                return  getClothingData();

            case myconstants.PERSONAL_CARE_CAMEL_CASE:
                return  getPersonalcareData();

            case myconstants.BABY_NEEDS_CAMEL_CASE:
                return  getBabyNeedsData();

            case myconstants.HEALTH_CAMEL_CASE:
                return  getHealthData();

            case myconstants.TECHNOLOGY_CAMEL_CASE:
                return  getTechnologyData();

            case myconstants.FOOD_CAMEL_CASE:
                return  getFoodData();

            case myconstants.BEACH_SUPPLIES_CAMEL_CASE:
                return  getBeachsuppliesData();

            case myconstants.CAR_SUPPLIES_CAMEL_CASE:
                return  getcarsuppliesData();

            case myconstants.NEEDS_CAMEL_CASE:
                return  getNeedsData();

            default:
                return new ArrayList<>();
        }
    }
}

