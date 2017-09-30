package ir.mastani.godzilla.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ir.mastani.godzilla.item.User;
import ir.mastani.godzilla.utils.Variables;

class Parser {

    static void parseUser(JSONObject json) {
        try {
            User user = new User();
            user.setName(json.getString("name"));
            user.setCard(json.getString("card"));
            user.setCharge(json.getString("charge"));

            Variables.me = user;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
