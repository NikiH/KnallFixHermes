package ch.hszt.kfh.rockstocks.zero;

/* 
 * Part of this code is Copyright (c) 1996 S.M.Bernsee under The Wide Open License.
 * 
 * Copyright (c) 2006, Karl Helgason
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 *    1. Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *    2. Redistributions in binary form must reproduce the above
 *       copyright notice, this list of conditions and the following
 *       disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *    3. The name of the author may not be used to endorse or promote
 *       products derived from this software without specific prior
 *       written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER
 * IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN
 * IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.util.Arrays;

public class PitchShift {
	
	/* CONVERSION TO JAVA BY: Karl Helgason (kalli@bigfoot.com), 2005 */
	
	/* ***************************************************************************
	 *
	 * NAME: smbPitchShift.cpp
	 * VERSION: 1.1
	 * HOME URL: http://www.dspdimension.com
	 * KNOWN BUGS: none
	 *
	 * SYNOPSIS: Routine for doing pitch shifting while maintaining
	 * duration using the Short Time Fourier Transform.
	 *
	 * DESCRIPTION: The routine takes a pitchShift factor value which is between 0.5
	 * (one octave down) and 2. (one octave up). A value of exactly 1 does not change
	 * the pitch. numSampsToProcess tells the routine how many samples in indata[0...
	 * numSampsToProcess-1] should be pitch shifted and moved to outdata[0 ...
	 * numSampsToProcess-1]. The two buffers can be identical (ie. it can process the
	 * data in-place). fftFrameSize defines the FFT frame size used for the
	 * processing. Typical values are 1024, 2048 and 4096. It may be any value <=
	 * MAX_FFT_FRAME_LENGTH but it MUST be a power of 2. osamp is the STFT
	 * oversampling factor which also determines the overlap between adjacent STFT
	 * frames. It should at least be 4 for moderate scaling ratios. A value of 32 is
	 * recommended for best quality. sampleRate takes the sample rate for the signal 
	 * in unit Hz, ie. 44100 for 44.1 kHz audio. The data passed to the routine in 
	 * indata[] should be in the range [-1.0, 1.0), which is also the output range 
	 * for the data, make sure you scale the data accordingly (for 16bit signed integers
	 * you would have to divide (and multiply) by 32768). 
	 *
	 * COPYRIGHT 1999-2003 Stephan M. Bernsee <smb [AT] dspdimension [DOT] com>
	 *
	 * 						The Wide Open License (WOL)
	 *
	 * Permission to use, copy, modify, distribute and sell this software and its
	 * documentation for any purpose is hereby granted without fee, provided that
	 * the above copyright notice and this license appear in all source copies. 
	 * THIS SOFTWARE IS PROVIDED "AS IS" WITHOUT EXPRESS OR IMPLIED WARRANTY OF
	 * ANY KIND. See http://www.dspguru.com/wol.htm for more information.
	 *
	 * ****************************************************************************/ 
	
	public final static double M_PI = Math.PI;
	public final static int MAX_FRAME_LENGTH = 8192;
	
	private double[] gInFIFO = new double[MAX_FRAME_LENGTH];
	private double[] gOutFIFO = new double[MAX_FRAME_LENGTH];
	private double[] gFFTworksp = new double[MAX_FRAME_LENGTH];
	
	private double[] gOutputAccum = new double[MAX_FRAME_LENGTH];
	private int gRover = -1;
	
	private int fftFrameSize; int osamp; double sampleRate;
	private int fftFrameSize2;
	private double[] window_table;
	/*
	 private final static int table_len = 80000;    
	 private final static double ffactor = table_len / ( 2* Math.PI);
	 private static double[] sintable = new double[table_len];
	 private static double[] costable = new double[table_len];
	 
	 private synchronized static void precalc()
	 {
	 sintable = new double[table_len];
	 double flen  = sintable.length;
	 int len = sintable.length;
	 for (int i = 0; i < len; i++) {
	 double k = ((i*1.0) / flen) * Math.PI * 2;
	 sintable[i] = Math.sin(k);
	 costable[i] = Math.cos(k);
	 }
	 }		
	 {
	 precalc();
	 }
	 
	 public final static double sin(double inp)
	 {
	 long s_index = ((long)(inp*ffactor)) % table_len;
	 if(s_index < 0) s_index += table_len;
	 return sintable[(int)s_index];
	 
	 }
	 public final static double cos(double inp)
	 {
	 long s_index = ((long)(inp*ffactor)) % table_len;
	 if(s_index < 0) s_index += table_len;
	 return costable[(int)s_index];
	 } 
	 */
	
	
	
	//private FFT fft;
	
	public static double[] wHanning(int fftFrameSize)
	{
		double[] window_table = new double[fftFrameSize];
		for (int k = 0; k < fftFrameSize;k++) {			
			window_table[k] = -.5*Math.cos(2.*Math.PI*(double)k/(double)fftFrameSize)+.5;			
		}			
		return window_table; 
	}

	public PitchShift(int fftFrameSize, int osamp, double sampleRate)
	{
		
		//fft = new FFT(fftFrameSize);
		/* initialize our static arrays */
		Arrays.fill(gInFIFO, 0);
		Arrays.fill(gOutFIFO, 0);
		Arrays.fill(gFFTworksp, 0);
		Arrays.fill(gLastPhase, 0);
		Arrays.fill(gSumPhase, 0);
		Arrays.fill(gOutputAccum, 0);
		Arrays.fill(gAnaFreq, 0);
		Arrays.fill(gAnaMagn, 0);
		
		this.fftFrameSize = fftFrameSize;
		this.osamp = osamp;
		this.sampleRate = sampleRate;
		
		
		
		
		fftFrameSize2 = fftFrameSize/2;
		
		stepSize = fftFrameSize/osamp;
		freqPerBin = sampleRate/(double)fftFrameSize;
		expct = 2.*M_PI*(double)stepSize/(double)fftFrameSize;
		inFifoLatency = fftFrameSize-stepSize;
		
		// Generate window function
		window_table = wHanning(fftFrameSize);
		/*
		 new double[fftFrameSize];
		 for (int k = 0; k < fftFrameSize;k++) {
		 window_table[k] = -.5*Math.cos(2.*M_PI*(double)k/(double)fftFrameSize)+.5;
		 }*/		
		
		// Analyze Formants of fftdata and fftmod
		// calculate peaks 250hz apart
		binsperformants = (int)(250 / freqPerBin);
		
		f_freqPerBin = (float)freqPerBin;
		f_expct = (float)expct;
		
		magnmap1 = new double[fftFrameSize2];
		magnmap2 = new double[fftFrameSize2];		
		amagnmap1 = new double[fftFrameSize2];
		amagnmap2 = new double[fftFrameSize2];
		last_amagnmap2 = new double[fftFrameSize2];		
		
		Arrays.fill(last_amagnmap2, 1);
		
	}	
	
	private int binsperformants;
	
	private double[] magnmap1;
	private double[] magnmap2;
	private double[] amagnmap1;
	private double[] amagnmap2;
	private double[] last_amagnmap2;
	
	private double[] runningsum = null;
	public void processFormatScan(double[] magnmap, double[] outdata)
	{	
		int runner_len = binsperformants;	
		
		if(runningsum == null)
			runningsum = new double[runner_len];
		
		double[] runningsum = this.runningsum;
		Arrays.fill(runningsum, 0.0); 
		
		int runner = 0;	
		double totalsum = 0;
		for (int i = 0; i < fftFrameSize2; i++) {
			double m = magnmap[i];
			
			totalsum -= runningsum[runner];
			totalsum += m;
			runningsum[runner] = m;
			runner++;
			runner%=runner_len;
			
			outdata[i] = (totalsum / runner_len); // The running sum	
		}
		
	}
	
	
	float f_freqPerBin, f_expct;
	
	double freqPerBin, expct;
	int inFifoLatency, stepSize;
	
	
	
	
	
	public void smbPitchShift(double formatpitchShift, double[] formatpitchtable, double pitchShift, double[] pitchtable, int p_start, int start, int end, int interlace, double[] indata, double[] outdata)
	/*
	 Routine smbPitchShift(). See top of file for explanation
	 Purpose: doing pitch shifting while maintaining duration using the Short
	 Time Fourier Transform.
	 Author: (c)1999-2002 Stephan M. Bernsee <smb [AT] dspdimension [DOT] com>
	 */
	{
		double window; //, magn, phase, tmp, real, imag;
		//double expct;
		int i,k; //, qpd, index;
		
		/* set up some handy variables */
		if (gRover == -1) gRover = inFifoLatency;
		
		/* main processing loop */
		int pi = p_start;
		
		double[] gInFIFO = this.gInFIFO;
		double[] gOutFIFO = this.gOutFIFO;
		double[] gOutputAccum = this.gOutputAccum;
		double[] window_table = this.window_table;
		double[] gFFTworksp = this.gFFTworksp;
		
		for (i = start; i < end; i+= interlace, pi++){
			
			/* As long as we have not yet collected enough data just read in */
			gInFIFO[gRover] = indata[i];
			outdata[i] = gOutFIFO[gRover-inFifoLatency];
			gRover++;
			
			/* now we have enough data for processing */
			if (gRover >= fftFrameSize) {
				gRover = inFifoLatency;
				
				if(pitchtable != null) pitchShift = pitchtable[pi];
				if(formatpitchtable != null) formatpitchShift = formatpitchtable[pi];
				
				/* do windowing and re,im interleave */
				for (k = 0; k < fftFrameSize;k++) {
					window = window_table[k];
					gFFTworksp[k] = (gInFIFO[k] * window);
					//gFFTworksp[2*k+1] = 0.;
				}
				
				/* ***************** ANALYSIS ******************* */
				/* do transform */
				
				calcReal(gFFTworksp, -1);
				
				boolean doformatpitch = false;
				if(Math.abs(formatpitchShift - pitchShift) > 0.001)
				{
					doformatpitch = true;
				}
				
				if(doformatpitch)
				{
					// Before Intensity Map
					for (int ii = 0; ii < fftFrameSize2; ii++) {
						double real = gFFTworksp[2*ii];
						double imag = gFFTworksp[2*ii+1];
						magnmap1[ii] = real*real + imag*imag;
					}				
				}
				
				// 13 CPU in Double prec.
				if(Math.abs(pitchShift - 1) > 0.001)
					processFFT(pitchShift, gFFTworksp);
				
				/* zero negative frequencies */
				//Arrays.fill(gFFTworksp, fftFrameSize+2 , 2*fftFrameSize , 0);
				
				if(doformatpitch)
				{
					// After Intensity Map
					for (int ii = 0; ii < fftFrameSize2; ii++) {
						double real = gFFTworksp[2*ii];
						double imag = gFFTworksp[2*ii+1];
						magnmap2[ii] = real*real + imag*imag;
					}		
					processFormatScan(magnmap1, amagnmap1);
					processFormatScan(magnmap2, amagnmap2);
					
					double[] magn_shift1 = amagnmap1;
					if(Math.abs(formatpitchShift - 1) > 0.001)
					{
						magn_shift1 = magnmap1;
						int fftFrameSize2_1 = fftFrameSize2 - 1;
						for (int j = 0; j < fftFrameSize2; j++) {
							int index = (int)(j/formatpitchShift);
							double scale = j/formatpitchShift - index;
							if (index < fftFrameSize2_1) {
								magn_shift1[j] = amagnmap1[index]*(1 - scale) + amagnmap1[index+1]*scale;
							}
							else
							{
								magn_shift1[j] = 0;
							}
						}						
					}
					
					
					for (int ii = 0; ii < fftFrameSize2; ii++) {			
						double amp;			
						if(amagnmap2[ii] == 0)
						{
							amp = 0;				
						}
						else
							amp = Math.sqrt(magn_shift1[ii] / amagnmap2[ii]);
						
						//amp = (last_amagnmap2[ii]*10 + amp)/11;
						//last_amagnmap2[ii] = amp;
						
						//if(i < first100HZ) amp = 1;
						
						gFFTworksp[2*ii] = gFFTworksp[2*ii]*amp;
						gFFTworksp[2*ii+1] = gFFTworksp[2*ii+1]*amp;
					}					
				}
				
				/* do inverse transform */
				
				calcReal(gFFTworksp, 1);
				
				/* do windowing and add to output accumulator */ 
				for(k=0; k < fftFrameSize; k++) {
					window = window_table[k];					
					gOutputAccum[k] += 2.*window*gFFTworksp[k]/(fftFrameSize2*osamp);
				}
				
				for (k = 0; k < stepSize; k++) gOutFIFO[k] = gOutputAccum[k];
				
				/* shift accumulator */
				//memmove(gOutputAccum, gOutputAccum+stepSize, fftFrameSize*sizeof(float));
				int shift_len = gOutputAccum.length / 2;
				for (int j = 0; j < shift_len; j++) {
					gOutputAccum[j] = gOutputAccum[j + stepSize]; 
				}
				
				
				/* move input FIFO */
				for (k = 0; k < inFifoLatency; k++) gInFIFO[k] = gInFIFO[k+stepSize];
			}
		}
	}
	
	
	
	private double[] gLastPhase = new double[MAX_FRAME_LENGTH/2+1];
	private double[] gSumPhase = new double[MAX_FRAME_LENGTH/2+1];
	private double[] gAnaFreq = new double[MAX_FRAME_LENGTH];
	private double[] gAnaMagn = new double[MAX_FRAME_LENGTH];
	private double[] gSynFreq = new double[MAX_FRAME_LENGTH];
	private double[] gSynMagn = new double[MAX_FRAME_LENGTH];
	
	private ComplexSimpleRadix2Transformer fft_realtocomplex, fft_complextoreal;
	
	public final void calcReal(double[] data, int sign)
	{
		if(sign == -1)
		{
			if(fft_realtocomplex == null) fft_realtocomplex = new ComplexSimpleRadix2Transformer(fftFrameSize,  -2);
			fft_realtocomplex.transform(data);
		}
		else
		{
			if(fft_complextoreal == null) fft_complextoreal = new ComplexSimpleRadix2Transformer(fftFrameSize,  2);
			fft_complextoreal.transform(data);
		}
	}

	
	public final void processFFT(double pitchShift, double[] gFFTworksp)
	{
		int k, qpd, index;
		double real, imag, magn, phase, tmp;
		
		double[] gAnaMagn = this.gAnaMagn;
		double[] gAnaFreq = this.gAnaFreq;
		double[] gSynMagn = this.gSynMagn;
		double[] gSynFreq = this.gSynFreq;				
		
		/* this is the analysis step */
		for (k = 0; k <= fftFrameSize2; k++) {
			
			/* de-interlace FFT buffer */
			real = gFFTworksp[2*k];
			imag = gFFTworksp[2*k+1];
			
			/* compute magnitude and phase */
			magn = 2.*Math.sqrt(real*real + imag*imag);
			phase = Math.atan2(imag,real);
			
			/* compute phase difference */
			tmp = phase - gLastPhase[k];
			gLastPhase[k] = phase;
			
			/* subtract expected phase difference */
			tmp -= (double)k*expct;
			
			/* map delta phase into +/- Pi interval */
			qpd = (int)(tmp/M_PI);
			if (qpd >= 0) qpd += qpd&1;
			else qpd -= qpd&1;
			tmp -= M_PI*(double)qpd;
			
			/* get deviation from bin frequency from the +/- Pi interval */
			tmp = osamp*tmp/(2.*M_PI);
			
			/* compute the k-th partials' true frequency */
			tmp = (double)k*freqPerBin + tmp*freqPerBin;
			
			/* store magnitude and true frequency in analysis arrays */
			gAnaMagn[k] = magn;
			gAnaFreq[k] = tmp;
			
		}
		
		
		/* ***************** PROCESSING ******************* */
		/* this does the actual pitch shifting */
		Arrays.fill(gSynMagn, 0);
		Arrays.fill(gSynFreq, 0);
		//int fftFrameSize2_1 = fftFrameSize2 - 1; 
		for (k = 0; k <= fftFrameSize2; k++) {
			index = (int)(k/pitchShift);
			//double scale = k/pitchShift - index;
			if (index <= fftFrameSize2) {
				//if (index <= fftFrameSize2_1) {
				gSynMagn[k] += gAnaMagn[index];
				//gSynMagn[k] += gAnaMagn[index]*(1-scale) + gAnaMagn[index]*scale;
				gSynFreq[k] = gAnaFreq[index] * pitchShift;
			}
		}
		
		/* ***************** SYNTHESIS ******************* */
		/* this is the synthesis step */
		for (k = 0; k <= fftFrameSize2; k++) {
			
			/* get magnitude and true frequency from synthesis arrays */
			magn = gSynMagn[k];
			tmp = gSynFreq[k];
			
			/* subtract bin mid frequency */
			tmp -= (double)k*freqPerBin;
			
			/* get bin deviation from freq deviation */
			tmp /= freqPerBin;
			
			/* take osamp into account */
			tmp = 2.*M_PI*tmp/osamp;
			
			/* add the overlap phase advance back in */
			tmp += (double)k*expct;
			
			/* accumulate delta phase to get bin phase */
			gSumPhase[k] += tmp;
			phase = gSumPhase[k];
			
			/* get real and imag part and re-interleave */
			gFFTworksp[2*k] = magn*Math.cos(phase);
			gFFTworksp[2*k+1] = magn*Math.sin(phase); 
			/*			
			 long s_index = ((long)(phase*ffactor)) % table_len;
			 if(s_index < 0) s_index += table_len;
			 
			 gFFTworksp[2*k] = magn*costable[(int)s_index];
			 gFFTworksp[2*k] = magn*sintable[(int)s_index];				  	
			 */
		} 
		
		
		
	}
	
	
}
