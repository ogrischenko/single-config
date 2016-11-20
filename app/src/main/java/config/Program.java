package config;

import run.Configurator;

import java.util.ArrayList;

class Program
{
    public static int main(String[] args) {
        return Run(args);
    }

    private static int Run(String[] args)
    {
        String fileName = null;

        if (args.length == 0)
        {
//            OpenFileDialog openFileDialog1 = new OpenFileDialog();
//
//            openFileDialog1.InitialDirectory = run.Configurator.GetBasePath();
//            openFileDialog1.Filter = "xml files (*.xml)|*.xml";
//            openFileDialog1.RestoreDirectory = true;
//
//            if (openFileDialog1.ShowDialog() == DialogResult.OK)
//            {
//                fileName = openFileDialog1.FileName;
//            }
        }
        else
        {
            fileName = args[0];
        }

        if (fileName == null)
        {
            return 0;
        }

        ArrayList<CmdArgument> arguments = new CmdArgumentsParser().GetCmdArguments(args);

        return new Configurator(arguments).Configure(fileName);
    }
}
