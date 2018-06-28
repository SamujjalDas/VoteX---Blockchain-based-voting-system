package com.blockchain.him.blockchainapp;

import android.Manifest;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;

import org.web3j.abi.datatypes.Array;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.Transfer;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import static org.web3j.tx.ManagedTransaction.GAS_PRICE;
import static org.web3j.tx.Transfer.GAS_LIMIT;

public class MainActivity extends AppCompatActivity {

    Web3j web3j;
    Credentials credentials;
    Voting voting;
    Task task;
    String walletAddress, contractAddress,hash;
    BigInteger cRon,cJohn,cHarry,wei;
    ProgressBar progressBar;
    TextView contractText,address,hC,rC,jC,txText,txHash;
    EthGetBalance ethGetBalance;
    Button ron,harry,john;
    boolean voted=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ui();
        initialise();

        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        task = new Task();
                        task.execute();
                    } else {
                        Toast.makeText(this, "Permissions needed", Toast.LENGTH_SHORT).show();
                    }
                });

        ron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!voted){
                    vote("Ron");
                }else{
                    Toast.makeText(MainActivity.this, "One Vote per account ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        john.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!voted){
                    vote("John");
                }else{
                    Toast.makeText(MainActivity.this, "One Vote per account ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        harry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!voted){
                    vote("Harry");
                }else{
                    Toast.makeText(MainActivity.this, "One Vote per account ", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void ui(){

        progressBar=findViewById(R.id.progressBar);
        contractText=findViewById(R.id.dappText);
        address=findViewById(R.id.dappAddress);
        ron=findViewById(R.id.Ron);
        john=findViewById(R.id.John);
        harry=findViewById(R.id.Harry);
        hC=findViewById(R.id.harryCount);
        rC=findViewById(R.id.ronCount);
        jC=findViewById(R.id.johnCount);
        txText=findViewById(R.id.txText);
        txHash=findViewById(R.id.txHash);

    }

    public void initialise(){
        progressBar.setVisibility(View.GONE);
        contractText.setVisibility(View.INVISIBLE);
        address.setVisibility(View.INVISIBLE);
        txText.setVisibility(View.INVISIBLE);
        txHash.setVisibility(View.INVISIBLE);

    }

    public class Task extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            web3j = Web3jFactory.build(new HttpService("https://rinkeby.infura.io/<your_token_here>"));
            try {
                Log.d("web3j", "Version" + web3j.web3ClientVersion().send().getWeb3ClientVersion());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                //pizel2
                credentials = WalletUtils.loadCredentials("<your_password_here>",
                        "/storage/emulated/0/Download/UTC--2018-06-27T15-23-52.791144500Z--dfa819a0d562c932c9e2ffaeb19198ab1c3c71c9.json");

                walletAddress = credentials.getAddress();

                Log.d("web3j", "Credentials loaded ");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CipherException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("task", "onPostExecute: ");
            Log.d("web3j", "onPostExecute: "+walletAddress);
            try {
                ethGetBalance=web3j.ethGetBalance("0xdfa819a0d562c932c9e2ffaec19198ab1c3c71c9", DefaultBlockParameterName.LATEST
                        ).sendAsync().get();
                wei= ethGetBalance.getBalance();
                Toast.makeText(MainActivity.this, "Balance "+wei, Toast.LENGTH_LONG).show();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            try {
                loadContract();
            } catch (Exception e) {
                e.printStackTrace();
            }

            progressBar.setVisibility(View.GONE);
            contractText.setVisibility(View.VISIBLE);
            address.setVisibility(View.VISIBLE);
            address.setText(contractAddress);
            rC.setText(String.valueOf(cRon));
            hC.setText(String.valueOf(cHarry));
            jC.setText(String.valueOf(cJohn));

        }


    }

    public void loadContract() throws Exception {

        voting = Voting.load(
                "0xf4c8b2a0A0F4b044e23b1FFd6ea30F7901cD7Cb5", web3j, credentials,Contract.GAS_PRICE,Contract.GAS_LIMIT);
       // BigInteger gas=new BigInteger("4000000");
       // voting.setGasPrice(gas);
        contractAddress = voting.getContractAddress();
        Log.d("address", contractAddress);
        cRon=voteCount("Ron");
        cJohn=voteCount("John");
        cHarry=voteCount("Harry");



    }

    public BigInteger voteCount(String name) throws Exception {

        BigInteger count;
        count= voting.totalVotesFor(stringToBytes32(name)).sendAsync().get();
        Log.d("count", "voteCount: "+count.toString());
        return count;

    }

    public void vote(String name) {

        voted=true;
        try {

            TransactionReceipt candidateName= voting.voteForCandidate(stringToBytes32(name)).sendAsync().get();
            Log.d("block", "vote: "+candidateName.getLogs());

           // progressBar.setVisibility(View.VISIBLE);
           /* Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    hash=candidateName.getBlockHash();
                    rC.setText(String.valueOf(cRon));
                    hC.setText(String.valueOf(cHarry));
                    jC.setText(String.valueOf(cJohn));
                    progressBar.setVisibility(View.INVISIBLE);
                    txHash.setVisibility(View.VISIBLE);
                    txText.setVisibility(View.VISIBLE);

                }
            },3000);
            */
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static byte[] stringToBytes32(String string) {

        byte[] byteValue = string.getBytes();
        byte[] byteValueLen32 = new byte[32];
        System.arraycopy(byteValue, 0, byteValueLen32, 0, byteValue.length);
        return (byteValueLen32);
    }
}
