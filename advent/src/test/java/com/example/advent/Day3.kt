package com.example.advent

import org.junit.Test

class Day3 {
    private val priority = "abcdefghijklmnopqrstuvwxyz" + "abcdefghijklmnopqrstuvwxyz".uppercase()

    @Test
    fun part1Sample() {
        val input = """
            vJrwpWtwJgWrhcsFMMfFFhFp
            jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
            PmmdzqPrVvPwwTWBwg
            wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
            ttgJtRGJQctTZtZT
            CrZsJsPPZsGzwwsLwLmpwMDw
        """.trimIndent()
        println(getPrioritySum(input))
        println(prioritiesSumOf3Lines(input))
    }

    private fun getPrioritySum(input: String): Int {
        var sum = 0
        input.split("\n").forEach { line ->
            val trimmed = line.trim()
            val half = trimmed.length / 2
            val part1 = trimmed.substring(0, half)
            val part2 = trimmed.substring(half, trimmed.length)
            val char = getRepeatingChar(part1, part2)
            sum += priority.indexOf(char) + 1
        }
        return sum
    }

    private fun getRepeatingChar(vararg parts: String): Char {
        val first = parts.first()
        first.forEach { char ->
            if (parts.all { part -> part.contains(char) }) {
                return char
            }
        }
        throw RuntimeException("no repeating char")
    }

    @Test
    fun part1() {
        val input = """
            FzQrhQpJtJMFzlpplrTWjTnTTrjVsVvvTnTs
            mScqSqqgcfPCqGPZcfGNSvTNsVVNSjNvWSNsNz
            fPcPGqgCcHgFzQpJJtHtJH
            DZDqqlrjplDHrNCmnBcHBMCRcJzb
            RQFLStFvdcBbzdJbJM
            PThQtwftTPFvtTPhvtFtfFtpZZllwjRNlsqNqqZjwpGlrZ
            pPwtqgwJZPJLgQqSFlqhFFlqMd
            DBmCWBBDWTRGvcVRTCCnnfQlFSdlzfhfdMWQfjhhQz
            drmBVVCRgprPtrZp
            HznjQjvmzDMVrQnMLJMMlfWgPSlJGWWJPl
            BdcqqhcdBRpFhhZBthhctdJSJJWfgGFlJCSFgbWPCDJS
            NdRTZdNqBwqtthpRBTTRqdtZrsLQVzrrzjzDwDsnmrQrnsrr
            HZFZCFzZWszqsRTBZTNMhmthVTmhDppmMQVPpm
            wjvSbJddvrvlrvnJSJJvlJmhPlhVPVtGVpQDBVMpphQP
            frbrfrcvvnvjfwbcJgrrCBRsCFsNzRgRCHCqssRH
            dDFNqNqZqPLNqvqTTvCLSPdZssGHClJQJcRHJGHHcHBcsMsQ
            lrjmWgWWrhjgrppQHHMQrsQRJGcBJc
            lVlmnwjmdTTSvVFN
            FWNFHvQPmLGwwwSHtswwln
            RfMJcDdfdcfdddfZjdchrtZmSmCZVtqVnZmrnrtC
            JMmJcfjjphcghpgjhRGzGzBBGPFGNBvPTpFL
            cVPVwStmmcQPBQPpSCppwhHZNNqHszNBhsNRNjqHzj
            MfWdDgvdbnvgMTWgvgZfzmsZJHzNhqjqjRhJ
            MDWMWGndMgFDnFLDwQrPPCSrCSVrlmGS
            QLZmPdRdWmMsMDWZmsLWWrhMHcHGzHvGzFcvrvzNrc
            tplSbLVBlvHHcFNnSr
            VqfgwLlCJWmWQTfW
            nRWvlvRbtLvdMCPFGL
            wrfsJNNGhNzGrTgDMDLgPMLPfq
            wcVhJQhwhrrBpmVblBRGSG
            HHHcggrZLcQQcQll
            GzfzTRTzmmFMwSNSwdSJQtNLNB
            TGbmLMFTzVVVTMzmFMfFPMHPZhnjZCpHnhgnZnPWCPZZ
            MRwwpVMHRspqVqwmccDlDrcHBBZgBl
            jQfQQQjWWFBgmcgDfcZg
            hvvSQzSnQQSWWQWSjTZVTRMshwVCssppwV
            pvrTvCvtFppCHMMZcdDFdcZM
            wLjTQnqljjSnlwjqjRgLcHHHMBDMZhBMHgHcbBDh
            mqjqlSNqRqwSRrWCvzGmtfTfzs
            TWScDCqCQQVBWDqWHsHswwBgRJzRhhHp
            dPttGrvFfGjMjnjvshsJgsJLgghRgH
            rFMlGdtjPffNnnrffSNcVCDqQqCQRqQRRN
            GmBRbVpPbmJcwggBBgWW
            LjsTCNNtddjHqLLgWwccqgfq
            nsjNjntNtjHCsDwZmwZZVmmGSvSD
            bwDDgNFtMMDbFsMbFwWWVcRcSpcgjgQWhWSp
            lfTJJlvdfCffccWppRjRlcSc
            RnzGdJJmsMNnMFtM
            bsBTFsqqTTmFZTsQBWWznWCRshlJNJlCVh
            GjGnDvDjvjPppHwwpwgrPPClJhNVRCzhhzJWlWlhNlvJ
            ffdgLrgdLrDjdfHPbbZbttcBbcbLmntn
            TNTwwvTTHNtTHNLLVqtqTSZBJnrnhhbrFJjZjnVZgghF
            cplWfRlzcWfRCZZhFrGjBfjZjn
            pddzDsRpDcclzCQMWBvNSmTTSqdvPPvqwqtT
            DQTttwwLtQtVSDMJDRmmSS
            ffsWfvrBWrPvwJhPhPSMPMVn
            WsvsggFvwNLgHtNQ
            llBbVDMTlFVdFDTbVggSVsqZqZZZqqvNJZJRNRWgtv
            HhpjcHHvjPsqCsWcNcsq
            GfpvnPvwFDTTFFDw
            GMmFGMGFFgVwQHQwwM
            cJtZNtZTbThcZtcZJJtTZWJPllgNgpPvVgpjHvQpRpHQNg
            hWcJZcnhcJznbcBZLqSLDfCmHqnqCLsD
            zQpjLpnhnsHTnlQLrMCCHPFrvvCMPcHm
            ZfgdSBtNqBwlgSDfZDwtqSFvJCvrPrVvFmwCJFvrmmFV
            dfbRNZBqDtgRNBNNNljLLjhGRGGWGLGTRhjz
            hhrnfBzhtzZgDgDnBfrfDZsRpMNCNNWjwCCfGQGGNGCGQC
            lcdPmHLSPDSdFDpQMLjCQQQCRGpN
            lJSSbmPdVdVvdHbvSDFHHPlZqgBnttzgTsssTrqgbZbsTT
            FsdsShrgggLDdbSDsgrGrlWHTpfRpTjjfFTzRTRjBWWp
            mPvqCmJCqJNnPvPNPCvvLTTVjHjzNWHHTWRBRVTWVz
            wJLvqPZmJtccncvZmJqqrghDGQwbdSGdsgGgQgQr
            zFwtNJGtNFlpnwHccZjZbcpprsmc
            PWQfBWhBgQgTWQRLThBqMSVDSbbDRsVDmsmZsSZDjr
            fvQfWBfLqfTqhLhCvNFttJlCwGrrCC
            fNrGLNrfNrGjllRRRPmWVL
            tbJdcFbSSssZSmmpFcsSbwDWVWBlllVPDnnjBFjDRnBF
            ZZJcvZctgNmmvMGhQm
            HhhjFRhgrcRTFLvWVJVQWJVHDHQJPP
            GwCmwBfGzfSCzCfwtmtzzJVWSVJJZrbWQQQqJJDZVJ
            mtfzpGdststtBmfmCwrGRFcTcvjngjFnRcLnpLLn
            rrwjdwLgVmVwHrfPCJPQBCBGmPtt
            ccNZqbNnMMblNpTlNpnhhBPSJsQhJtJtChPJqS
            vTWvNcWNWTFvnnvcgjzDLVQLgHVwWDrW
            jNPgbNHbfLJgLzfz
            ShvhhFVVDShFVqMSSSvZfffvPLtBBBBJJlpfLJJv
            DqhnShhMnZZwCSDCMhChrRnNrNdNQbHNNPmjmdHN
            VQVZGQFnzFTSsBfgzgfs
            rjlpjtDrtMLZPMtPtpPZPwCsgSHgMHCCmCTWsgBWSBmg
            pjvDqLwrlDtwqtqNLvtjpPPwRNbQRncQVQddZhRhJQbJncbG
            PsBSqnSdQsFhmmmnppFc
            TRhNvrTCvNTHVcfHbJVTpc
            rhtWvGWLrjRqdSqqLLqdld
            vPhfqPJvrMrnffDDhvpMjdzGMLdLLQpllLGQ
            mbmcFSScGbSCcQlzwQQlclsg
            BSGVCmCTZWCGGvnvfZHqqrDhHN
            GSRfrzGRhzsGChjTBBlqBgjgCTCn
            wHQwtDVDHwHHDJcDWJZwzHZBqTnnBFlvjFgBqnljjvBdBZ
            JNmVJpVmNtDHJWHrbfPLhbGhrzRbpr
            WcWcbzNPbDwBNvWBwRMPQmJZQRQZftRZGP
            LhVHFgggTHCFHhfMQQSMMGQRMLLM
            qnrqppFVHphqfDsNbzjrzbrN
            cwgDrdLSrBrvvhDzCljjTW
            VHtVZpspQtMQsVRQppFVQVHtCdPTPTzdjvhTzTTPRvjjvWhn
            QQZpMdJsQFJHtMHdScwLwLJGrSScSwqw
            ZsjNflGfRfRPrZNRFcffLwJdwcLdDBnwzzzDznVn
            CTGvhhTqbtbgTqLJWdDntzWWdnLw
            phCMgmQGvvHCvMhbTQQFsNsNFPZSfZjffmNsll
            CNpCJHLNhhSSHZPgrFlFFWgpFpmzjj
            qQttDVDwQGdQGvqDQfwbcVrrlljjzzmzrVJgrr
            nvMDsqqqQvfvsqDnRSZHJPPZHhLHLS
            RNNrrPfDNRQwQhjscghMqs
            WVZlHvnZqtlLVLvwjwhsggTstMhwTw
            vGHWLJlVWlmLVqRCGCFFNfqqGf
            MNzqCnvqvqvCVLBvvCVCpVcRssncrPSTWGrPSPdGTcrP
            hmHwFmQjFlhtZmHwtZjjddSSGcsdPrrGcQQQRGPW
            fHbbFjlhZwmtwhfjmmwmmLbpLqzqvBzLzCvLNRMbNB
            tQfLrtQPrrfDSSCVlDfLSrmbBjGvWjjLmWWWpWNNppmv
            wdHhRTTndnRThdvnBFGpNBMnpvvp
            JdqTHTHHRdqzsJRRzTRHscJdDSGCfDlqQZqlfZrZZCffqSSQ
            hQMWLsgGJMMhsCHggQWhgspDWFPzZvPvptDvzvmtdtdF
            BrBlrTBrNRbfnjNQlZDztPvpmpppmzvfdd
            jQlQlqQVbVcsMgMgChhJVs
            MtFMCTWRFRRtCRTTRTMGJddjLdstHvBzBHzHVVpL
            lZSDnbDlnZPrbHpzJJsdSVJpBL
            nNghhPrlZlgDTFhCfMFJRMQF
            RGpPFZPRQZPFRGvpPQPpjvpmhnnCMjhmhgBgVgMVWBVgVM
            wLtfNdNHmrNthCBgCbhnngWd
            srSfwHfszsNmtswlrqQDGQFDRPJGDvzRppRJ
            GVFFGvVWZLFsmssFRNfVvmGGJPpJTTqDBvTpqlpDvqbBtTPl
            gQhzzChzrMQhjpzlzWzJpPpBJb
            ghgWjcCjMgCHWdQMhdjChCmfwmRRGZZGVHLZHRfmNwVs
            DnDVhdnrfSfpcGGjQQGdJddJ
            bPWPRbRsRMsHNzDqTZcGBcqZqmmN
            HvwPvvzMPwDCChDVwS
            vTCCvTfWFDTtRPMvfWFlDFHBqGLpLzbwBgWwqzGqbBbB
            cQcSNchSJSZShVJNnZrhSqBpgwGHHtGwqtbwLbqpbr
            JNnJVsJscNstNhQsjnVVNlFfMmTMFfCTfjFvfPRPPF
            VLFBsgffNFNqRvbz
            ChltjTdjDhHpHZvdpjjZhwCpbNrbSzzbrNGMTMMNSMbWWNSN
            vQjpttQhHnLsBQVLsQ
            mbzQgTzRVVbsVdQgzzVRddmztFGWNGNNWnGtFSGBsrCNWCrC
            jfJjvPPwLDcHDPvDDPDppLCWCFBGWntCBnrtFcrFWTGn
            wpJPLjvpTTDpwhfgzmVMbqhdhVRgzl
            PlcqbWClLmnqZVLq
            THwdrrhddhhfJJhwLJhpQnDVnznnmZQQnSpfpD
            vrFdvGsGHhhhwHjFGrFGJHdMCCcNgbWMPccRRccMFLNPPP
            tbppJqcNtJnZzRJbPFsFPHfZrrshFDjj
            GdwgwlLgGCndsDFrhDHHFF
            SSlLnmmvqWNqmcqb
            ZPFPPTZpZSWzCMMSzPBsFvhtlQvJQQtJhsVs
            dmNbmgbrwDNmbcDgwNdcwdLsnhlJlnvtsBJnhVQqqnstLB
            bNGfDGgHHVwbwNwVfgmRMzCzzCSHjSRZSZCTRS
            dDTffQdqQQLBLnVLLQvL
            rrBHZZcgJcrLvNLtLgRLbN
            cjjJhrFlhZwFFzwJzmTBBdmTsDPzDsBP
            ClGrJJMNCrGQqlcPvWgnDP
            ZBvbjHpSwBVVVcWjjjqQ
            BLSbbwsHSTBHwmLHHLbBsSTFdrfvCrtmdzfGJzrdzGJddGfh
            gljWRwmSjtJWjJtJjgjSZfVSTVVHGZSVHcVchZ
            pBzLFQpPsFBGcGBTThfB
            pFpQzFLPLpvQFQnLbsqqGddgjbmwRldwtWmlGWwj
            PDQDMFQBMfWPvjdLLndLjrmsMj
            qZqVzTRRqHtvZGGtVqTTzVjLLsrmJCddnLjrjHsrhdCr
            GzwcZtqNzqvNqwzZVGRwSzbpWfFbWPlWFpNDBfQfFNNf
            dfRszdzVdsjwdhLwCCqwGllHvPGPwG
            SpJtBLFgcGqHQClqZF
            JrttrtcTmSSLrmtBTrNgnBJjbNhhbhzRdsVdMhNjhMMhVd
            MPFSCfSMqVSBGrtzlvccfQctzbzl
            hZNjTHWWTZwshbLvmlWpBzmbmm
            dRTTJNDNhjsJqBBMMgrJPVVr
            WnVzDMjlDVWwwHgwhmgNhNNsJh
            qfvrLNCcbLdvpcvbrPPqCsGhSJGTTBspTshBpTBBms
            ZLvvZfrPfPCLbCFFzjVQzRnNNMVzDQ
            nllbFTTpTFTBcnCjQPqQdZRQZhCb
            tvWszrrztvSmzQQvrDmZRjjjPPDVqPRdZRdCPd
            gfzvSsftgQHQHgQl
            GVbHRRGRLpdmGWTm
            gSPPltPlrlvccFccPlcJNCTpnnmpMCLMMmWfdRmMSS
            FzNJRhhvPFRvQwzqjqzBHZZj
            PhZSpFBPBFsNmjBVllltBj
            JMGLnrrnbfffrdqRqPHnnqLDVTDDjgmRgwtmjDljlDVlwl
            LHMqPqPnnqGLWJPMnndrGfSWppzvvFSChFFFvvzQSQZz
            RSWWssbvnnCqZnWsRCnssWrTggNhgbNHBgQjhhQBgjNT
            mcpzcppzczcDGVcPcDLLGLjmrMNTNtQNHhMHrQBQNTgN
            LVpPfcjjWvsFFnFf
            MpddpdCpJdJlbdMvBHMnnsHqSRvG
            PWvZfFmZrrfmwWwFznBnqRRSGcsBVmVBRG
            zjzzhQPQvzjLPQzwffrwrtlTCDtJDlgJLltpTTJlTl
            TvTWjjzpznGttFFZccrrPrSZllcB
            gNNSqHMqsMHQJHNZCDDCZDqLZdlZBD
            SMQNSRNbRRHwhwhsRmtnvWVmmnbGnjmpGn
            ccSVQjCQddTsFJcH
            gLppBfgfmvCRFdsddTJJgb
            WMLMmWGGBZWZLCtvDhlSSDGlwhSPSzSP
            TpqVGVHFQGmqSqPZdccNCzzhdwCjNG
            fffbbvftMrBMDDcCccCZCjlvhCCd
            RLWMnbftDhnMRtfBftRJMtLMgFgHmmpmPmSmmQFPPLHHVTQS
            nRvwQSDNcpVJJcJR
            qZMjBhjhZMMBzLBGLGrjJbTPVTpbdPPdVbVb
            ZZpmFFZlfGqfmmGMzlfmMmnWQDtHtSvnWWNSHSSstFtS
            bFDGZjGDbbRSgLtN
            CphJVfJWCTBgvfLHNRcwnt
            WVhPWBTzzChzhhhBmrpPPCJZDQtdMlrjFQdrFqsjdrQsFG
            ZBpVQHHVMMWWdmmLWw
            lQhhrjcRttrqbvQLNwdDWzmNSDmStz
            QbGqhcbvcsqvCCHnsCZHCnTn
            tlWtQTTTJjTQtVnmrbnPWVShVC
            MDMGGzsHcwFgGZBqrmmPSnbqVmNVGC
            sZFPwHcMZDBRTlvQQJttTQTR
            FhVRfGptMGMnZhRFBNRBCCNHHNvTNTRC
            zmwrLLSjrbzmNlcvvrHvDPCN
            JLwjQdSbjdbSdqJQFGVqFVMgnGHMfGVV
            fffZWrJqZSHWTWHqSvrgDhggzRjttsDhpDgs
            PGlBLcBBbnnbLLFbGLBjRgjFTFVzshtzpgsppz
            TGCPnMPQlGnPmclPlnnQmbmHJvNvfHdqwddwvvZfCNHCfW
            ClLwpspTPrTFZCdzFbZdbQ
            RRMWfRgWVRMRQBZZScVczVGFbjNb
            MfnvMqWmslvDhQPw
            hdndSdqsTddBhdcmmNHFDcqHttPF
            JjMzzMZQGwZGZJzMzZJQzGJFvPvNPtFmvmNmDvcFtvDHMv
            gZwzQwJfGVJQJbGLBsSTSTdTbCWDBSnd
            ZZCHZRzMZGRMhMMVVFNThrdd
            SgsccSPmmgqssSlqsgcmscSqlhpFdVThjphNrdrhjdwdhFJN
            vmttqTcqvLqqmPccmqSBbRWnWzQZZZZBHnQCzHDH
            GgPnGdSPBpGsLTBL
            rVNJjmwZqtZZshltFTtvRFsL
            mqmWrZVqWjrqZMNwPMQQbsddgdsbsgPz
            LZLVvjZrggHLJggSZDgrnPnQnRnppVRllntRdPFz
            chMCzbqGmhNhhbBCMBdFnpfqFnltRRQnlPpQ
            TChmWcMMTmBswJzZZrWrvzgg
            gngRNBNRBsNFFBgfgbLLLnqdSLvLTcbLbd
            GWtlChlVMllcZSDWSLbdZL
            lljjGlhMGrGJpsFdRJfsfzfz
            jVTdrnGQcQtTTTFQqBqsgHHFgsqf
            ZZLbPLzDzPZCmsgqsBHt
            wDzDlPblRDPLPvhvwtdnnhdrnrMGWMVGMThj
            spjjpjvjpjmQjrpCMfSlfzrPBl
            dHFntHWnnbRVFtnbcqHFzBCCCPzfPMlcCSlgllzc
            RLbVWHnnSWtnHFbdbVRdNNtQsjsQTjDLwmGTmTssQwmLGJ
            JbJJSLMhRMSLhNqqwFDwFNcFqL
            GcpnGnznnpzpzGpffNTNTwTfwdDNNdTFdD
            nllnlPGWQWHcGpzzQGGzGvHGJbVVtJSChQVbmtmVJrmrmbRm
            GFsFrzwrflmtdtbltG
            ggLPDngCJncNLJRDwgnllmJqjWMjhjhjWWmWjj
            nBNRNPgpRgDLTgNwfsSHVBQHVHwsZr
            WwvnvWvcFtwtSFSF
            zBZZZRQSzMBSgSVJGjGTPTGFzCzmmj
            fZDrpZZfRfMgSQDDBhgQghDHsnbrcNlWnnLWHLrHsWnllc
            ZVncdPPwVPdhZngnqHWHNNvTHvlMvn
            fSLjjLSGGBjTTHqvBqrMNT
            RSSSDGRtSGZthTTctmtg
            rtzrfJbgJHRfGRZLPR
            hdVhlllmFlFPLwHmsRGGZP
            nTWhRjTBTWlvNQgnJSSbrJtz
            JgVTpBpfvgpTDDJFJvTgggtlFlNNMRLNNzNNZRNHMRCLlF
            wbPWcSGbGqWDlnNWMMMCLMWZ
            wrsGcbrcbcqwDwbcmGvQBQgTTsdVJgJsVdQf
            mztrhgJtDrhgcrZmnhbnzbhcMTMPlBCPBGVGTMVGslCCPGDs
            FLRQmjjFSQpQwLlPsMsCpvslvPCB
            fNLLwSdSwWSWjwmrtczZhhrJzdzh
            HHwCwJFmHZttZCfCSffSMHcVDMcPBRPcPRDhPghM
            nvQLsTnLslnLvpzGTssnsRPDMhPgVPVgtcVMRPgVQQ
            vnsTGWlTLsWTLLvNsGWlsZrwmZCJddjFmtJJNZFftj
            hbjSTvSJTfcSwcPSPfTbfHszVVFpGnpJpsHFnHVVls
            rtZrcQrRZZQrmZBQlCGppnppHzpVFCGR
            WmLqmgNtcLNQWTbPvfPwbbdb
            HzZgsdHglHlzdHsFtsNNJSlNcSpjcjlrrNVv
            wqqWRPPqwmbcqPjQVvSPJJrVpv
            qqBBqmWRhqRLqcBnhzzztgnTdDHnHsFsHn
            rJPFVwwsrJwmdVrLWJvvRBWBvbzWlb
            nDZcNGNpjTpHncvpZCDnTNZGhlWzQhWbpRRQlQhpWWSWLlQb
            CDNntnCCHnvmqPfwtFdVqd
            gqBwgBjCswwgqNBNCVDDTVdhlSDTDcZc
            HvRRFMzRRRRMpHrtTllfhZHHSShHTf
            PmlGLPrppMrrmFFmLMWRjbsjnsjwQNJWnbQjWgBN
            pDggpFgRghZjBFPPnPPFrt
            cwTfLwBVwCWbLcVTVVvrdndGjMHrnGJtnttdMC
            NTVcWNvcBSpgNqspRQlN
            DLDgFlDmNZfjfnJZSF
            tctvttzvGGzvrHqtVVdwnJGSSnnjjZdWTdwW
            zvpcrbpHpqJJsPbPlLlhmhglPQ
            pvHHvssFCFZQNCftttdQdd
            VgTGTTVGgLjDjlLGzgPVMTNwmcwQmMQfQtmdcmwMJwNm
            TPjTDjfGWTLLljgzrWpZZbsqrFqhqbps
            ppVLcfcwSLgpSLVLgWwtfshDNDqvWvGvlQZvDNHQHjqq
            MPrzmdRrPPrCJFnMnMRRFRPdqqZQNQvjvZDGDlHhQvGNDG
            BmBMBBJTMmPBJMMFCCFJRmrsTlVpVbpwLSVwLsgcwTVlVc
            SSGzmFRzmRGLgSSmGMJFnvfvJnJVnJQnMl
            cBpjHtjwNfcpNZtppHtCMlMPMlJBVlVQlvJPvJ
            dNtNZwqWfqtqZWtHttsqHqrRrrdRTLbmmzSLmTGGmbrg
            RrrddnrgnRbbgWdGrfnwgQwjDjDpvTpBQTwBPP
            MHCStZJzSwvPjWQD
            mcJWVHCCLcGLbdcn
            PlMsdjPdGMjdPSrSjgddbLbmHHTszHZzpHmsTFvmpzZzmN
            ntRJQVRfcQhcQWhnchBJWntTFTTTNTSpFtztmZFDTpDZ
            hQfcfCBSwCccVJhSJnrPPGLqPlbPLCrqldgb
            vgvWDMZvGpcqgqsP
            tSdtjLHLQLHjdFdDddQSQhwlsGqwQlqqqhQsPhGc
            tbRjtTLFRvTZDBrMrV
        """.trimIndent()
        println(getPrioritySum(input))
        println(prioritiesSumOf3Lines(input))
    }

    private fun prioritiesSumOf3Lines(input: String): Int {
        var sum = 0
        val split = input.split("\n")
        for (i in split.indices step 3) {
            val first = split[i]
            val second = split[i + 1]
            val third = split[i + 2]

            sum += priority.indexOf(getRepeatingChar(first, second, third)) + 1
        }
        return sum
    }
}
