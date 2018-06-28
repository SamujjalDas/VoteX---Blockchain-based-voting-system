package com.blockchain.him.blockchainapp;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.4.0.
 */
public class Voting extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b5060028054600160a060020a0319163317905561031b806100326000396000f3006080604052600436106100825763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416632f265cf78114610087578063392e6678146100b55780637c9f7518146100e1578063887c4251146100fb578063b13c744b14610113578063cc9ab2671461013d578063f447b94814610155575b600080fd5b34801561009357600080fd5b5061009f60043561016d565b6040805160ff9092168252519081900360200190f35b3480156100c157600080fd5b506100cd600435610199565b604080519115158252519081900360200190f35b3480156100ed57600080fd5b506100f96004356101e6565b005b34801561010757600080fd5b506100cd60043561023f565b34801561011f57600080fd5b5061012b600435610254565b60408051918252519081900360200190f35b34801561014957600080fd5b506100f9600435610273565b34801561016157600080fd5b5061009f6004356102da565b600061017882610199565b151561018357600080fd5b5060009081526020819052604090205460ff1690565b6000805b6003548110156101db5760038054849190839081106101b857fe5b60009182526020909120015414156101d357600191506101e0565b60010161019d565b600091505b50919050565b60025473ffffffffffffffffffffffffffffffffffffffff16331461020a57600080fd5b600380546001810182556000919091527fc2575a0e9e593c00f959f8c92f12db2869c3395a3b0502d05e2516446f71f85b0155565b60016020526000908152604090205460ff1681565b600380548290811061026257fe5b600091825260209091200154905081565b61027c81610199565b151561028757600080fd5b60008181526001602052604090205460ff16156102a357600080fd5b600090815260208181526040808320805460ff19808216600160ff9384168101909316179092559283905292208054909216179055565b60006020819052908152604090205460ff16815600a165627a7a7230582038192cb3d7934c190a6d7d8980b635095d08bf7a3962465eb0f511454185fe790029";

    public static final String FUNC_TOTALVOTESFOR = "totalVotesFor";

    public static final String FUNC_VALIDCANDIDATE = "validCandidate";

    public static final String FUNC_SETCANDIDATENAME = "setCandidateName";

    public static final String FUNC_VOTED = "voted";

    public static final String FUNC_CANDIDATELIST = "candidateList";

    public static final String FUNC_VOTEFORCANDIDATE = "voteForCandidate";

    public static final String FUNC_VOTESRECIEVED = "votesRecieved";

    protected Voting(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Voting(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<BigInteger> totalVotesFor(byte[] candidate) {
        final Function function = new Function(FUNC_TOTALVOTESFOR, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(candidate)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Boolean> validCandidate(byte[] candidate) {
        final Function function = new Function(FUNC_VALIDCANDIDATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(candidate)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<TransactionReceipt> setCandidateName(byte[] candidateNames) {
        final Function function = new Function(
                FUNC_SETCANDIDATENAME, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(candidateNames)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Boolean> voted(byte[] param0) {
        final Function function = new Function(FUNC_VOTED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<byte[]> candidateList(BigInteger param0) {
        final Function function = new Function(FUNC_CANDIDATELIST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteCall<TransactionReceipt> voteForCandidate(byte[] candidate) {
        final Function function = new Function(
                FUNC_VOTEFORCANDIDATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(candidate)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> votesRecieved(byte[] param0) {
        final Function function = new Function(FUNC_VOTESRECIEVED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public static RemoteCall<Voting> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Voting.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Voting> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Voting.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static Voting load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Voting(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Voting load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Voting(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
