package info.aea.drawer;

import info.aea.snippets.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment_CompileEngine extends Fragment {
	
	public Fragment_CompileEngine(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_compile_engine, container, false);
         
        return rootView;
    }
}