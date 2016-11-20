package config;

import java.util.ArrayList;

class CmdArgumentsParser {
    public ArrayList<CmdArgument> GetCmdArguments(String[] args) {
        ArrayList<CmdArgument> res = new ArrayList<CmdArgument>();

        for (String arg : args) {
            String[] arr = arg.split("=");

            if (arr.length != 2) {
                continue;
            }

            res.add(new CmdArgument(arr[0], arr[1]));
        }

        return res;
    }
}