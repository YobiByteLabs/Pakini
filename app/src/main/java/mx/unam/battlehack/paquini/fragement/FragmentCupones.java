package mx.unam.battlehack.paquini.fragement;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mx.unam.battlehack.paquini.R;
import mx.unam.battlehack.paquini.adaptador.CuponesAdapter;
import mx.unam.battlehack.paquini.controlador.ActivityMain;
import mx.unam.battlehack.paquini.objetos.Constants;
import mx.unam.battlehack.paquini.util.ManejadorListas;

public class FragmentCupones extends Fragment implements RecyclerView.OnScrollListener {


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Context context;
    private static final String ARG_SECTION_NUMBER = "section_number";

    public static FragmentCupones newInstance(int sectionNumber) {
        FragmentCupones fragment = new FragmentCupones();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new CuponesAdapter(ManejadorListas.cupones,context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cupones, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnScrollListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((ActivityMain) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
        context = activity;
    }

    @Override
    public void onScrollStateChanged(int i) {

    }

    @Override
    public void onScrolled(int i, int i2) {
        try{
            Log.d(Constants.TAG,"Es visible"+ mLayoutManager.findViewByPosition(ManejadorListas.cupones.size()-1).isInTouchMode());
        }catch(Exception e){

        }
    }


}
