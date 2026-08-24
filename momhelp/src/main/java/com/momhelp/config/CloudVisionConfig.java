package com.momhelp.config;

import org.springframework.context.annotation.Configuration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudVisionConfig {

    public static final String CLARIFAI_API_URL = "https://api.clarifai.com/v2/models/food-item-recognition/outputs";
    public static final String CLARIFAI_API_KEY = "ce2fd709ea0d402c96cb52abe6292646";
    
    public static final String[] SUPPORTED_FORMATS = {"jpg", "jpeg", "png", "webp"};
    public static final long MAX_FILE_SIZE = 5 * 1024 * 1024;
    
    // Enhanced vegetable detection with Indian names and synonyms
    public static final Map<String, String[]> VEGETABLE_SYNONYMS = new HashMap<String, String[]>() {{
        // Peppers & Chilies
        put("chili", new String[]{"chili", "chilli", "pepper", "capsicum", "bell pepper", "hot pepper", "green chili", "red chili", "mirchi"});
        put("bell pepper", new String[]{"bell pepper", "capsicum", "sweet pepper", "shimla mirch"});
        
        // Okra
        put("okra", new String[]{"okra", "lady finger", "ladyfinger", "bhindi", "ladies finger", "ladies' finger"});
        
        // Tomato
        put("tomato", new String[]{"tomato", "tamatar", "cherry tomato"});
        
        // Potato
        put("potato", new String[]{"potato", "aloo", "potatoes"});
        
        // Onion
        put("onion", new String[]{"onion", "pyaz", "shallot", "spring onion", "scallion"});
        
        // Garlic & Ginger
        put("garlic", new String[]{"garlic", "lahsun", "garlic clove"});
        put("ginger", new String[]{"ginger", "adrak", "ginger root"});
        
        // Leafy Greens
        put("spinach", new String[]{"spinach", "palak", "leaf", "greens", "green vegetable"});
        put("cabbage", new String[]{"cabbage", "patta gobi", "red cabbage"});
        put("lettuce", new String[]{"lettuce", "salad", "salad greens"});
        
        // Cruciferous
        put("cauliflower", new String[]{"cauliflower", "phool gobi", "gobi"});
        put("broccoli", new String[]{"broccoli", "hari phool gobi"});
        
        // Root Vegetables
        put("carrot", new String[]{"carrot", "gajar", "carrots"});
        put("radish", new String[]{"radish", "mooli", "daikon"});
        put("beetroot", new String[]{"beetroot", "beet", "chukandar"});
        put("turnip", new String[]{"turnip", "shalgam"});
        put("sweet potato", new String[]{"sweet potato", "shakarkand", "yam"});
        
        // Squashes
        put("pumpkin", new String[]{"pumpkin", "kaddu", "squash", "winter squash"});
        put("cucumber", new String[]{"cucumber", "kheera", "kakdi"});
        put("zucchini", new String[]{"zucchini", "courgette", "tori"});
        
        // Eggplant
        put("eggplant", new String[]{"eggplant", "brinjal", "baingan", "aubergine"});
        
        // Peas & Beans
        put("pea", new String[]{"pea", "matar", "green pea", "peas", "garden pea"});
        put("bean", new String[]{"bean", "beans", "green bean", "french bean", "sem"});
        
        // Corn
        put("corn", new String[]{"corn", "maize", "bhutta", "sweet corn", "corn on the cob"});
        
        // Others
        put("mushroom", new String[]{"mushroom", "mushrooms", "khumbh"});
        put("bitter gourd", new String[]{"bitter gourd", "karela", "bitter melon"});
        put("bottle gourd", new String[]{"bottle gourd", "lauki", "doodhi", "calabash"});
        put("ridge gourd", new String[]{"ridge gourd", "turai", "tori"});
    }};
    
    // Lowercase confidence threshold for detection
    public static final double CONFIDENCE_THRESHOLD = 0.5; // 50% instead of 70%
}