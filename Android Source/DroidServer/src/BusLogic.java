/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Kyle
 */
public class BusLogic ///add connection to db/data io
{
    public static final String SYNCLOT = "synclot";
    public static final String SYNCSTATES = "sync";
    public static final String CLOSE_C = "close";
    public static final String CANCEL = "cancel";
    public static final String SWAP = "swap";
    public static final String REQUEST = "request";

    
    public String processRequest(String cRequest)
    {
        if (cRequest != null && cRequest.contains(SYNCLOT))
        {
            //bayId, x, y, rot
            return "0000,2,2,0";
        }
        else if(cRequest != null && cRequest.contains(SYNCSTATES))
        {
            //bayID, state
            return "0000,1";
        }
        else if(cRequest != null && cRequest.contains(CLOSE_C))
        {
            
        }
        else if(cRequest != null && cRequest.contains(CANCEL))
        {
            
        }
        else if(cRequest != null && cRequest.contains(SWAP))
        {
            
        }
        else if(cRequest != null && cRequest.contains(REQUEST))
        {
            //ask server for avail bay if any
        }
        
        return "Invalid Request";
    }
    
}
